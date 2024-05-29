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


async function getList({currentUser, sellingCarId, page, size}){

    //console.log({currentUser, sellingCarId, page, size})

    const result = await axios.get(`/buyingCar/list`, {params: {userName:currentUser, sellingCarId, page, size}})

    //console.log(result.data)
    return result.data
}

// async function getHighProposalPrice(sellingCarId){
//
//     const result = await axios.get(`/buyingCar/highProposalPrice`, {params: {sellingCarId}})
//
//     //console.log(result.data)
//     return result.data
// }

