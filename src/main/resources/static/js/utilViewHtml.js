
// 테이블 페이지 출력
function printPages(data, formObj){
    let pageStr = '';

    if(data.prev){
        pageStr += `<li class="page-item"><a class="page-link" data-page="${data.start-1}">PREV</a></li>`
    }
    for(let i = data.start; i <= data.end; i++){
        pageStr += `<li class="page-item ${i === data.page ? "active":""} "><a class="page-link" data-page="${i}">${i}</a></li>`
    }
    if(data.next){
        pageStr += `<li class="page-item"><a class="page-link" data-page="${data.end+1}">NEXT</a></li>`
    }
    formObj.innerHTML = pageStr
}

// 파일 불러오기 정보 표시
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

// 파일 업로드 모달창 내용 초기화
function initModalFileUpload(files){
    files.value = ''
    console.log('initModalFileUpload()!!')
}

// 파일 업로드 내용 표시 부분 초기화
function initUploadResult(uploadResult){
    uploadResult.innerHTML = ''
    console.log('initUploadResult()!!!')
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


