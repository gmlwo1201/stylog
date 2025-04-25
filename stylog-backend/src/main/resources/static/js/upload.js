// upload.js
document.addEventListener("DOMContentLoaded", function () {
   // HTML에서 요소를 찾아서 변수에 담아
   const fileInput = document.querySelector('input[type="file"]');
   const addIcon = document.querySelector('.add-icon');
 
   // 아이콘 클릭했을 때 실행되는 함수
   if (fileInput && addIcon) {
     addIcon.addEventListener('click', () => {
       fileInput.click();
     });
   }
 });