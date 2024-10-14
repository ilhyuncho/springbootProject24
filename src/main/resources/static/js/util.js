
function stringToDateFormat(stringData){
    let date = new Date(stringData)

    const format = date.getFullYear() + '-' + ( (date.getMonth()+1) <  9 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)) +
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

// 이미지 파일 [바로 삭제] 또는 [임시 삭제] 처리
function removeFileData(uuid, fileName, direct, checkMain, obj){

    const targetDiv = obj.closest('.card')

    if(checkMain && checkMainImage(fileName)){
        alert('메인 이미지는 삭제 할수 없습니다')
        return
    }
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

function checkMainImage(removeFileName){
    const checked = document.querySelector('input[name="isMainImage"]:checked').value
    return checked === removeFileName;
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

function sellingCarLike(isLike){
    const formObj = {
        carId:carId,
        isLike:isLike,
    }

    sendLike(formObj).then(result => {  // axios 호출

        viewCarLike(isLike)

    }).catch(e => {
        errorResponse(e)
    })
}



// input text 숫자  ',' 추가----begin-------------------------

function transComma(e){

    let value = e.value;
    value = Number(value.replaceAll(',', ''));
    if(isNaN(value)) {
        e.value = 0;
    }else {
        const formatValue = value.toLocaleString('ko-KR');
        e.value = formatValue;
    }
}

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


// [포인트 전액 사용] 버튼 클릭
function useAllPoint(){

    const expectedPriceValue = parseInt(expectedPrice.value.replaceAll(",",""))
    const totalMPointValue = parseInt(totalMPoint.value.replaceAll(",",""))
    useMPoint.value = expectedPriceValue > totalMPointValue ? totalMPointValue.toLocaleString('ko-KR') : expectedPriceValue.toLocaleString('ko-KR')

    reCalculation()
}

// 랜덤 숫자 생성
function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

async function callRemoveFiles() {

    const removeFailResult = []

    for ({uuid, fileName} of removeFileList) {   // 비동기 처리를 위해 for...of 를 사용

        await removeFileToServer(uuid, fileName).then(result => {

            if (result['removed'] === false) {
                removeFailResult.push(fileName)
            }
        })
    }
    return removeFailResult
}

// html 페이지 로딩시 호출
function pageInit(){

    // 새로운 알림 정보 있는지 요청
    checkNewAlarm(true)
}

// 고객에게 온 새로운 알림이 있는지 체크
function checkNewAlarm(checkLocal){

    let isRefreshNewAlarm = false;
    const newAlarm = localStorage.getItem("newAlarm");

    if(!newAlarm || checkLocal === false){
        isRefreshNewAlarm = true
    }else{
        const now = new Date()
        const item = JSON.parse(newAlarm)
        if(now < item.expiry){
            // 알림 마크 설정
            changeAlarmMark(item.value)
        }
        else{
            isRefreshNewAlarm = true
        }
    }
    // DB에서 새로운 알림 있는지 체크
    if(isRefreshNewAlarm){
        resetNewAlarmInfo()
    }
}

function resetNewAlarmInfo(){

     getNewAlarm().then(data=>{
        // localStorage 에 새롭게 등록
        const now = new Date()
        const item= {
            value:data,
            expiry:now.getTime() + ( 1000 * 10 )    // 10초
        }
        localStorage.setItem("newAlarm", JSON.stringify(item));

        changeAlarmMark(data)

    }).catch(e=>{
        console.error(e)
    })
}



