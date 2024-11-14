<div id="readme-content"></div>

<script>
fetch("README.md") // Markdown 파일 경로
  .then((response) => response.text())
  .then((markdownText) => {
    const htmlContent = marked(markdownText); // Markdown -> HTML로 변환
    document.getElementById("readme-content").innerHTML = htmlContent; // 웹에 표시
  });
</script>
