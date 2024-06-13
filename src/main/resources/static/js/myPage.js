
async function findMyCar(formObj) {
   // console.log(formObj)

    const response = await axios.get(`/myPage/findMyCar`,{params: {carNumber:formObj.carNumber}})

   // console.log(response)
    return response.data
}

async function RegisterMyCar(formObj) {
     console.log(formObj)

    const response = await axios.post(`/myPage/carRegisterNew`,formObj)

    // console.log(response)
    return response.data
}
