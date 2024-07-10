
async function getMyPoint(formObj){
    const response = await axios.get(`/myInfo/myPoint`, {params: formObj})
  //  console.log(response)
    return response.data
}

async function getDeliveryAddress(formObj){
    const response = await axios.get(`/myInfo/addressInfo`, {params: formObj})
    //  console.log(response)
    return response.data
}

async function sendDeliveryAddress(formObj) {
    console.log(formObj)

    if(formObj.callType === 'register'){
        const response = await axios.post(`/myInfo/registerAddress`, formObj)
        console.log(response)
        return response.data
    }
    else if(formObj.callType === 'modify'){
        const response = await axios.post(`/myInfo/modifyAddress`, formObj)
        console.log(response)
        return response.data
    }
}

async function deleteDeliveryAddress(userAddressBookId){

    const result = await axios.post(`/myInfo/deleteAddress/${userAddressBookId}`)

    console.log(result)

    return result.data
}