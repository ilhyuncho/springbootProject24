async function requestBuyingCar(formObj) {

    console.log(formObj)

    const response = await axios.post(`/buyingCar/offer`, formObj)

   // console.log(response)
    return response.data
}

async function getListBuyingCar({sellingCarId, page, size}){

    const result = await axios.get(`/buyingCar/list`, {params: {sellingCarId, page, size}})

    //console.log(result.data)
    return result.data
}

