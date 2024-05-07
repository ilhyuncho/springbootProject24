async function registerAuction(formObj) {

    console.log("register Auction....................")
    console.log(formObj)

    const response = await axios.post(`/auction/register`, formObj)

    console.log(response)
    return response.data
}

async function getAuction({auctionId}){
    const result = await axios.get(`/auction/get`, {params: {auctionId}})

    console.log(result.data)
    return result.data
}