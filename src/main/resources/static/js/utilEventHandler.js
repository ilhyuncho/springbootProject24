// 모달창의 [구매 제안], [경매 응찰 취소], [가격 수정] 처리
document.querySelectorAll(".cancelBtn, .sendRegisterBtn, .sendModifyBtn").forEach(function(item,idx){
    item.addEventListener("click", function (e) {
        e.stopPropagation()
        e.preventDefault()

        const offerType = e.target.getAttribute("data-bs-target")

        makeFormAndSendToServer(offerType)
    })
}, false)



// [상담 요청], [경매 요청] 전송
document.querySelectorAll(".sendConsultRequestBtn, .sendAuctionRequestBtn").forEach(function(item,idx){
    item.addEventListener("click", function (e) {
        e.stopPropagation()
        e.preventDefault()

        const offerType = e.target.getAttribute("data-bs-target")

        makeFormAndSendToServer(offerType)
    })
}, false)

// [모달창] 파일 업로드 닫기
document.querySelectorAll(".closeUploadBtn").forEach(function (item,idx){
    item.addEventListener('click', function(){

        const fileInput = document.querySelector("input[name='files']")
        console.log('closeUploadBtn click!!!!!!!!')

        initModalFileUpload(fileInput)  // 파일 업로드 모달 창 입력 값 초기화
        uploadModal.hide()
    })
}, false)


// [모달창] 닫기
document.querySelectorAll(".closeModalBtn").forEach(function (item,idx){
    item.addEventListener('click', function(e){
        console.log('closeModalBtn()!!!!!!! e: ' + e)

        initModalData(e)    // 모달창 초기화
    })
}, false)











