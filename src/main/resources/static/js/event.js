async function getNotiList(formObj){

    const response = await axios.get(`/notification/list`, {params: formObj})

    console.log(response)
    return response.data
}