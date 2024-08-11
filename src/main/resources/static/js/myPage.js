
async function findMyCar(formObj) {
   // console.log(formObj)

    const response = await axios.get(`/myPage/findMyCar`,{params: {carNumber:formObj.carNumber}})

   // console.log(response)
    return response.data
}

async function updateCarKm(formObj) {
    console.log(formObj)

    const response = await axios.post(`/myPage/updateCarKm`, formObj)

    console.log(response)
    return response.data
}


async function registerMyCar(formObj) {
     //console.log(formObj)

    const response = await axios.post(`/myPage/carRegister`,formObj)
        // .catch(function (error) {
        //     if (error.response) {
        //         console.log("요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.");
        //         console.log(error.response.data);
        //         console.log(error.response.status);
        //         console.log(error.response.headers);
        //     }
        //     else if (error.request) {
        //         console.log("요청이 이루어 졌으나 응답을 받지 못했습니다.");
        //         // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
        //         // Node.js의 http.ClientRequest 인스턴스입니다.
        //         console.log(error.request);
        //     }
        //     else {
        //         console.log("오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.");
        //         // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
        //         console.log('Error', error.message);
        //     }
        //
        // });
     //console.log("response : " + response)
    return response.data
}
