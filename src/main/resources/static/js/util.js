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

    let str = `<div class="card col-4">
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

