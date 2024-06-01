async function getList({currentUser, sellingCarId, page, size}){

    //console.log({currentUser, sellingCarId, page, size})

    const result = await axios.get(`/buyingCar/list`, {params: {userName:currentUser, sellingCarId, page, size}})

    //console.log(result.data)
    return result.data
}
