async function registerConsumable(formObj) {
    console.log(formObj)

    const response = await axios.post(`/consumable/register`, formObj)

    console.log(response)
    return response.data
}
