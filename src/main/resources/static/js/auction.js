async function registerAuction(formObj) {

    console.log("register Auction....................")
    console.log(formObj)

    const response = await axios.post(`/auction/register`, formObj)

    console.log(response)
    return response.data
}