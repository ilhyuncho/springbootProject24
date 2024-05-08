async function purchaseOffer(formObj) {

    console.log("purchaseOffer....................")
    console.log(formObj)

    const response = await axios.post(`/buyingCar/offer`, formObj)

    console.log(response)
    return response.data
}

async function getList({carId, sellingCarId, page, size}){
    // goLast : 마지막 페이지 호출 여부

    console.log({carId, sellingCarId, page, size})

    const result = await axios.get(`/buyingCar/list`, {params: {sellingCarId, page, size}})

    console.log(result.data)

    return result.data
}