function stringToDateFormat(stringData){
    let date = new Date(stringData)

    let format = date.getFullYear() + '-' + ( (date.getMonth()+1) <  9 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)) +
        '-' + ( (date.getDate()) <  9 ? "0" + (date.getDate()) : (date.getDate()));

    return format;
}

// 금액 + '원'
function makePriceCurrency(data){
    return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '원'
}

const maskingName = (value) => {
    if (value.length === 2) {
        return value.replace(/(?<=.{1})./gi, '*');
    } else if (value.length > 2) {
        return value.replace(/(?<=.{1})./gi, '*');
    } else {
        return value;
    }
}

function getUploadFileNames(){
    const uploadFiles = uploadResult.querySelectorAll("img") // uploadResult : 업로드 미리 보기

    const fileNameList = []
    for(let i = 0 ; i < uploadFiles.length;i++){
        const uploadFile = uploadFiles[i]
        const imgLink = uploadFile.getAttribute("data-src")

        fileNameList.push(imgLink)
    }

    return fileNameList
}
function appendFileData(){
    // form태그에 현재 첨부파일 정보를 추가
    const target= document.querySelector(".uploadHidden")
    const uploadFiles = uploadResult.querySelectorAll("img")

    let str= ''
    for(let i= 0; i < uploadFiles.length;i++){
        const uploadFile = uploadFiles[i]
        const imgLink = uploadFile.getAttribute("data-src")

        str += `<input type='hidden' name='fileNames' value="${imgLink}">`
    }
    target.innerHTML = str;
}

// 첨부파일 업로드 후 화면에 표시
function showUploadFile({uuid, fileName, link}, direct, index){

    console.log(index)

    let str = `<div class="card col-3">
            <div class="card-header d-flex">`

    if( index === 0){
        str += `<input type="radio" name="isMainImage" checked value="${fileName}" >메인</input>`
    }
    else{
        str += `<input type="radio" name="isMainImage" value="${fileName}" >메인</input>`
    }

    str += `&nbsp;&nbsp; ${fileName}
              <button class="btn-sm btn-danger" onclick="javascript:removeFileData('${uuid}', '${fileName}', ${direct}, this)" >X</button>
            </div>
            <div class="card-body">
                <img src="/view/${link}" data-src="${uuid+"_"+fileName}">
            </div>
           </div>`

    uploadResult.innerHTML+=str
}

// 이미지 파일 바로 삭제 또는 임시 삭제 처리
function removeFileData(uuid, fileName, direct, obj){

    const targetDiv = obj.closest(".card")

    if( direct === true){
        // if(!confirm("파일을 삭제!!")){
        //     return
        // }
        removeFileToServer(uuid, fileName).then(data =>{
            targetDiv.remove()
        })
    }
    else{ // 일단 임시로 삭제한 파일 저장
        removeFileList.push({uuid, fileName})
        targetDiv.remove()
    }
}

function appendNotShownData(){
    if(removeFileList.length === 0) {
        return
    }
    const target = document.querySelector(".uploadHidden")

    let str= ''
    for(let i = 0; i < removeFileList.length; i++){
        const {uuid, fileName} = removeFileList[i];

        str += `<input type='hidden' name='fileNames' value="${uuid}_${fileName}">`
    }
    target.innerHTML += str;
}

function getMainImageFileName(){
    const isMainImage = document.querySelector('input[name="isMainImage"]:checked')
    if(isMainImage !== null){
        return isMainImage.value
    }
    return null
}

function errorResponse(e){
    if (e.response && e.response.status === 400) {
        //alert('잘못된 요청입니다.. 전달값 확인!! status = 400')
        alert(e.response.data.message)
        console.log(e.response.data);
        console.log(e.response.data.message);

    } else {
       // alert('잘못된 요청입니다.. 전달값 확인!!')
        console.log(e.response.data);
        alert(e.response.data.message)
    }
}

// [모달창] 파일 업로드 닫기
document.querySelectorAll(".closeUploadBtn").forEach(function (item,idx){
    item.addEventListener('click', function(){

        const fileInput = document.querySelector("input[name='files']")
        console.log('closeUploadBtn click!!!!!!!!')

        initModalFileUpload(fileInput)  // 파일 업로드 모달 창 입력 값 초기화
        uploadModal.hide()
    })
}, false)

function initModalFileUpload(files){
    files.value = ''
    console.log('files.value init')
}

function initUploadResult(uploadResult){
    uploadResult.innerHTML = ''
    console.log('uploadResult.innerHTML')
}

// 모달창의 [기본 배송지 설정] 체크 박스 클릭시
function checkMain(isCheck){
    if(isCheck === true){
        checkSolid.style.display = 'inline'
        checkRegular.style.display = 'none'
        modalMainAddressCheck.value = 'true'
    }
    else if(isCheck === false){
        checkSolid.style.display = 'none'
        checkRegular.style.display = 'inline'
        modalMainAddressCheck.value = 'false'
    }
}

function sellingCarLike(isLike){
    const formObj = {
        carId:carId,
        isLike:isLike,
    }

    sendLike(formObj).then(result => {

        viewCarLike(isLike)

    }).catch(e => {
        errorResponse(e)
    })
}

function viewCarLike(isLike){
    if(isLike === true){
        checkSolid.style.display = 'inline'
        checkRegular.style.display = 'none'
    }
    else if(isLike === false){
        checkSolid.style.display = 'none'
        checkRegular.style.display = 'inline'
    }
}

// input text 숫자  ',' 추가----begin-------------------------
// 새로운 응찰가격 입력시 콤마 추가
function newPriceKepUp(obj){
    // input text 숫자  ',' 추가
    numberAddComma(obj);
}

function numberAddComma(obj){
    const num = getNumber(obj.value);

    if(num === 0){
        obj.value = '';
    }else{
        obj.value = num.toLocaleString();
    }
}

function getNumber(strNumber){
    const arr = strNumber.split('');
    const out = [];
    for(let cnt=0;cnt<arr.length;cnt++){
        if(isNaN(arr[cnt])===false){
            out.push(arr[cnt]);
        }
    }
    return Number(out.join(''));
}
// input text 숫자  ',' 추가----end-------------------------