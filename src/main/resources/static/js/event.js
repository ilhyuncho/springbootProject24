async function getNotiList(formObj){

    const response = await axios.get(`/notification/${formObj.pathName}`, {params: formObj})

    console.log(response.data)
    return response.data
}