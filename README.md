<h1>Documentação da API de Armazenamento de Arquivos</h1>

<p>A API de Armazenamento de Arquivos permite fazer upload, download e listar arquivos armazenados no servidor.</p>

<h2>Base URL</h2>

<pre><code>http://localhost:8080/api/file</code></pre>

<h2>Endpoints Disponíveis</h2>

<h3>Upload de Arquivo</h3>

<p>Realiza o upload de um arquivo para o servidor.</p>

<ul>
    <li><strong>URL</strong>: <code>/upload</code></li>
    <li><strong>Método HTTP</strong>: <code>POST</code></li>
    <li><strong>Parâmetros do Formulário</strong>:
        <ul>
            <li><code>file</code>: Arquivo a ser enviado</li>
        </ul>
    </li>
</ul>

<h4>Exemplo de Requisição</h4>

<pre><code>POST /api/file/upload
Content-Type: multipart/form-data

[file contents here]
</code></pre>

<p>Resposta de Sucesso:</p>

<pre><code>Status: 200 OK
Content-Type: text/plain

File uploaded successfully with URL: [URL do arquivo]

</code></pre>

<h3>Download de Arquivo</h3>

<p>Realiza o download de um arquivo do servidor.</p>

<ul>
    <li><strong>URL</strong>: <code>/download/{fileName}</code></li>
    <li><strong>Método HTTP</strong>: <code>GET</code></li>
    <li><strong>Parâmetros de Caminho</strong>:
        <ul>
            <li><code>fileName</code>: Nome do arquivo a ser baixado</li>
        </ul>
    </li>
</ul>

<h4>Exemplo de Requisição</h4>

<pre><code>GET /api/file/download/nome_do_arquivo</code></pre>

<p>Resposta de Sucesso:</p>

<p>O arquivo é baixado automaticamente pelo navegador ou pelo cliente HTTP utilizado.</p>

<h3>Listagem de Arquivos</h3>

<p>Lista todos os arquivos armazenados no servidor.</p>

<ul>
    <li><strong>URL</strong>: <code>/list</code></li>
    <li><strong>Método HTTP</strong>: <code>GET</code></li>
</ul>

<h4>Exemplo de Requisição</h4>

<pre><code>GET /api/file/list</code></pre>

<p>Resposta de Sucesso:</p>

<pre><code>Status: 200 OK
Content-Type: application/json

[
    "nome_arquivo1.txt",
    "nome_arquivo2.jpg",
    ...
]
</code></pre>

