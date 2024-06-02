async function getNotiList(formObj){

    const response = await axios.get(`/notification/list`, {params: formObj})

    console.log(response.data)
    return response.data
}