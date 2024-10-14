
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
              <button class="btn-sm btn-danger" onclick="javascript:removeFileData('${uuid}', '${fileName}', ${direct}, true, this)" >X</button>
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

// 배송지 추가하기 모달창 셋팅
function showDeliveryAddress() {

    modalTitle.innerHTML = '배송지 추가'
    modalMainAddressCheck.value='false'

    checkSolid.style.display='none'
    checkRegular.style.display='inline'
    modalType.value = 'register'

    addressRegisterModal.show()
}

// [배송지 선택 모달창] 주소 선택시 (체크박스)
function addressSelect(index, obj){

    const checkboxes = document.getElementsByName("modalCheckAddress");
    checkboxes.forEach((cb) => {
        cb.checked = false;
    })
    obj.checked = true;
}

// [배송지 변경] 버튼 클릭
function selectAddress(){

    const formObj = {memberId:currentUser}

    getAllDeliveryAddress(formObj).then(result => {
        // 기존 행 삭제
        while (tableAddressList.firstChild) {
            tableAddressList.removeChild(tableAddressList.firstChild);
        }

        let _html = `<div class="dialog">`

        if(result && result.length > 0) {
            result.forEach(function (e, i) {

                const isMainAddress = e.mainAddressCheck

                _html += `<div class="dialog">
                        <div class="dialog_inner">
                             <input type="hidden" name="modalSelectIndex">
                             <div class="input-group mb-3">
                                <input type="checkbox" data-bs-target=${e.userAddressBookId} name="modalCheckAddress"
                                    onchange="addressSelect( ${i}, this )"/>
                                <h5>${e.deliveryName}`

                if(isMainAddress){
                    _html += `<i class="fa-solid fa-circle" style="color: blueviolet"></i>`
                }

               _html += `       </h5>
                             </div>
                             <div class="input-group mb-3">
                               <p>${e.recipientName}/${e.recipientPhoneNumber}</p>
                               <p>${e.fullAddress}</p>
                             </div>
                             <hr class="my-4">
                        </div>`

            })
        }
        _html += '</div>'

        const dom = new DOMParser().parseFromString(_html, 'text/html');
        const dialog = dom.querySelector(".dialog");

        let newRow = tableAddressList.insertRow(-1);
        let newCell = newRow.insertCell(0);
        newCell.appendChild(dialog);

        addressListModal.show()

    }).catch(e => {
        errorResponse(e)
    })
}

function changeAlarmMark(isNew){

    const selectedAlarm = document.getElementById("alarmMark");

    if(isNew){
        selectedAlarm.style.color = "red"
    }
    else{
        selectedAlarm.style.color = "blue"
    }
}