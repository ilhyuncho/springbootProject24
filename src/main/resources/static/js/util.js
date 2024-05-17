function stringToDateFormat(stringData){
    let date = new Date(stringData)

    let format = date.getFullYear() + '-' + ( (date.getMonth()+1) <  9 ? "0" + (date.getMonth()+1) : (date.getMonth()+1)) +
        '-' + ( (date.getDate()) <  9 ? "0" + (date.getDate()) : (date.getDate()));

    return format;
}

// 금액 + '원'
function makePriceCurrency(data){
    return data.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '원'
}

const maskingName = (value) => {
    if (value.length === 2) {
        return value.replace(/(?<=.{1})./gi, '*');
    } else if (value.length > 2) {
        return value.replace(/(?<=.{1})./gi, '*');
    } else {
        return value;
    }
};