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

function getUploadFileNames(uploadFiles){
    const fileNameList = []
    for(let i = 0 ; i < uploadFiles.length;i++){
        const uploadFile = uploadFiles[i]
        const imgLink = uploadFile.getAttribute("data-src")

        console.log("imgLink:" + imgLink)

        fileNameList.push(imgLink)
    }

    return fileNameList
}

function appendFileData(){
    // form태그에 현재 첨부파일 정보를 추가
    const target= document.querySelector(".uploadHidden")
    const uploadFiles = uploadResult.querySelectorAll("img")

    let str= ''
    for(let i=0; i<uploadFiles.length;i++){
        const uploadFile = uploadFiles[i]
        const imgLink = uploadFile.getAttribute("data-src")

        str += `<input type='hidden' name='fileNames' value="${imgLink}">`
    }
    target.innerHTML = str;
}

function showUploadFile({uuid, fileName, link}){
    const str = `<div class="card col-4">
                <div class="card-header d-flex justify-content-center">
                    ${fileName}
                 <button class="btn-sm btn-danger" onclick="javascript:removeFile([[${uuid}]], [[${fileName}]], this)" >X</button>
                 </div>
                 <div class="card-body">
                    <img src="/view/${link}" data-src="${uuid+"_"+fileName}">
                 </div>
                 </div>`

    uploadResult.innerHTML+=str
}