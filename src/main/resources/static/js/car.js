
async function modifyCarInfo(formObj) {
    //console.log(formObj)

    const response = await axios.post(`/myPage/carModify`,formObj)
    console.log("response: " + response)
    console.log("response.data: " + response.data)

    return response.data
}