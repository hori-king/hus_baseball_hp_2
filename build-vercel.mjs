import { cp, mkdir, readFile, writeFile } from 'node:fs/promises';

const resources = new URL('./src/main/resources/', import.meta.url);
const output = new URL('./dist/', import.meta.url);
const backend = 'https://hus-baseball-hp.onrender.com';
const template = (name) => readFile(new URL(`templates/${name}.html`, resources), 'utf8');

const [index, head, header, footer] = await Promise.all(
  ['index', 'fragments/head', 'fragments/header', 'fragments/footer'].map(template),
);
const expandLinks = (html) => html
  .replace(/th:(href|src)="@\{(\/[^}" ]*)\}"/g, (_, attr, path) => {
    const local = path === '/' || /^\/(css|js|images)\//.test(path);
    return `${attr}="${local ? '' : backend}${path}"`;
  })
  .replace(/\s+th:fragment="[^"]*"/g, '');

const page = expandLinks(index
  .replace(/<head\b[\s\S]*?<\/head>/, () => head
    .replace(/<title[^>]*>.*?<\/title>/, '<title>北海道科学大学 準硬式野球部</title>')
    .replace(/<meta name="description"[^>]*>/, '<meta name="description" content="北海道科学大学準硬式野球部の公式ホームページ">')
    .replace(/<link rel="canonical"[^>]*>/, ''))
  .replace(/<div th:replace="~\{fragments\/header[^"]*"\s*><\/div>/, () => header.replace(/<li th:if="[^"]*"[\s\S]*?<\/li>/g, ''))
  .replace(/<div th:replace="~\{fragments\/footer[^"]*"\s*><\/div>/, () => footer)
  .replace(/<div th:if="\$\{param.logout\}"[\s\S]*?<\/div>/, '')
  .replace(/<article\b[\s\S]*?<\/article>/g, ''));

if (/\bth:(replace|href|src|each|if)=/.test(page) || !page.includes(backend + '/inquiry')) {
  throw new Error(page.split('\n').filter(line => line.includes('th:')).join('\n'));
}

await mkdir(output, { recursive: true });
await cp(new URL('static/', resources), output, { recursive: true });
await writeFile(new URL('index.html', output), page);
