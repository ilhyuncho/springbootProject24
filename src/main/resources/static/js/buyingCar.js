async function purchaseOffer(formObj) {

    console.log("purchaseOffer....................")
    console.log(formObj)

    const response = await axios.post(`/buyingCar/offer`, formObj)

   // console.log(response)
    return response.data
}

async function cancelOffer(formObj) {

    console.log("cancelOffer....................")
    console.log(formObj)

    const response = await axios.post(`/buyingCar/cancel`, formObj)

    //console.log(response)
    return response.data
}


async function getList({sellingCarId, page, size}){

    const result = await axios.get(`/buyingCar/list`, {params: {sellingCarId, page, size}})

    //console.log(result.data)
    return result.data
}

async function sendRequestConsult(formObj) {
    console.log(formObj)

    const response = await axios.post(`/buyingCar/requestConsult`, formObj)

    console.log(response)
    return response.data
}


// async function getHighProposalPrice(sellingCarId){
//
//     const result = await axios.get(`/buyingCar/highProposalPrice`, {params: {sellingCarId}})
//
//     //console.log(result.data)
//     return result.data
// }

