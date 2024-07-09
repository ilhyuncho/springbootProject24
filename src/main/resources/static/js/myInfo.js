
async function getMyPoint(formObj){
    const response = await axios.get(`/myInfo/myPoint`, {params: formObj})
  //  console.log(response)
    return response.data
}

async function sendDeliveryAddress(formObj) {
    console.log(formObj)

    const response = await axios.post(`/myInfo/deliveryAddress`, formObj)

    console.log(response)
    return response.data
}
