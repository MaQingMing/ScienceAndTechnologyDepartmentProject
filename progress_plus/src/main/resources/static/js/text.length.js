/**
 * 截取文本长度
 * @param value
 * @param width
 * @returns {string|*}
 */
function substringAndLength(value,width) {
    if (value == "" || value == undefined) {
        value = "-";
        return value;
    } else {
        if(typeof (width) == "undefined" || width == null || width<=200){
            return value;
        }else{
            let len = calculateElementsInWidth(parseInt(width),value);
            if(len<=0 || (value.length <= len)){
                return value;
            }
            if(isNumeric(value) || isAlphabetic(value)){
                return value.substr(0, len - 2) + "...";
            }else{
                return value.substr(0, len - 5) + "...";
            }
        }
    }
}

/**
 * 判断纯数字
 * @param str
 * @returns {boolean}
 */
function isNumeric(str) {
    //去掉空格
    if(typeof str === 'string'){
        str = str.split(/[\t\r\f\n\s]*/g).join('');
    }
    return /^\d+$/.test(str);
}

/**
 * 判断纯英文
 * @param str
 * @returns {boolean}
 */
function isAlphabetic(str) {
    //去掉空格
    if(typeof str === 'string'){
        str = str.split(/[\t\r\f\n\s]*/g).join('');
    }
    return /^[a-zA-Z]+$/.test(str);
}

/**
 * 计算在给定宽度下，可以放置多少个纯文本、纯数字、纯英文或混合的元素
 * @param width
 * @param textType
 */
function calculateElementsInWidth(width, textType) {
    // 假设一个元素的平均宽度为10（可以根据实际情况调整）
    let averageElementWidth = 13;
    if(isNumeric(textType) || isAlphabetic(textType)){
        averageElementWidth = 8;
    }
    // 计算可以放置的元素数量
    const numberOfElements = Math.floor(width / averageElementWidth);
    return numberOfElements;
}