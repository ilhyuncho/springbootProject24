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

// [모달창] 이미지 업로드 모달창 view - 버튼 클릭

document.querySelectorAll(".uploadFileBtn").forEach(function (item,idx){
    item.addEventListener('click', function(e){
        e.stopPropagation()
        e.preventDefault()

        uploadModal.show()
    })
}, false)


// 이미지 순서 수정 요청
document.querySelectorAll(".updateImageOrderBtn").forEach(function (item,idx){
    item.addEventListener('click', function(e){
        e.stopPropagation()
        e.preventDefault()

        const imageOrder = uploadResult.querySelectorAll('.inputOrder')
        const notiId = e.target.getAttribute("data-num")
        const imageOrderList = []

        for(let i= 0; i < imageOrder.length;i++){
            const inputValue = imageOrder[i]
            const imageId = inputValue.getAttribute("data-src")

            imageOrderList.push({imageId:imageId, imageOrder:inputValue.value})
        }

        const formObj = {
            objectId:notiId,
            imageOrderList:imageOrderList
        }

        modifyImageOrder(formObj).then(result => {
            console.log(result)

        }).catch(e => { // HttpStatus.OK 가 아니면.. catch 구문을 탐
            console.log(e.response.data.message)
            alert('catch: ' + e.response.data.message)
        }).finally(e=>{
            // 화면 재 갱신
            resetlocation(notiId)
        })

    })
}, false)



// [포인트 사용] 수동 입력
document.querySelectorAll("#useMPoint").forEach(function (item,idx){
    item.addEventListener('change', function(e) {
        e.stopPropagation()
        e.preventDefault()

        const totalMPointValue = parseInt(totalMPoint.value.replaceAll(",", ""))
        const useMPointValue = parseInt(useMPoint.value.replaceAll(",", ""))


        if (useMPointValue < 0) {
            useMPoint.value = 0
        }
        else if (useMPointValue > totalMPointValue) {
            useMPoint.value = totalMPointValue.toLocaleString('ko-KR')
        }
        else{
            useMPoint.value = useMPointValue.toLocaleString('ko-KR')
        }
        reCalculation()
    })

}, false)


// [모달창] 배송지 선택
document.querySelectorAll(".modalSelectBtn").forEach(function (item,idx){
    item.addEventListener('click', function(e) {

        const checkboxes = document.getElementsByName("modalCheckAddress");
        checkboxes.forEach((cb) => {
            if (cb.checked === true) {
                // 선택된 배송지 정보 다시 요청
                getAddressInfo(cb.getAttribute("data-bs-target"))
            }
        })

        addressListModal.hide()
    })
}, false)

