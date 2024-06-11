
async function getMyPoint(formObj){
    const response = await axios.get(`/myInfo/myPoint`, {params: formObj})
  //  console.log(response)
    return response.data
}
