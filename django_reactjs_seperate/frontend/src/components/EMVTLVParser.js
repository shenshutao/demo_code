// EMV TLV解析器
class EMVTLVParser {
  constructor() {
    // EMV标签定义
    this.tagDefinitions = {
      // 常见的EMV标签
      '00': { name: 'Payload Format Indicator', description: '负载格式指示符' },
      '01': { name: 'Point of Initiation Method', description: '启动方法' },
      '02': { name: 'Merchant Account Information', description: '商户账户信息' },
      '03': { name: 'Merchant Account Information', description: '商户账户信息' },
      '04': { name: 'Merchant Account Information', description: '商户账户信息' },
      '05': { name: 'Merchant Account Information', description: '商户账户信息' },
      '06': { name: 'Merchant Account Information', description: '商户账户信息' },
      '07': { name: 'Merchant Account Information', description: '商户账户信息' },
      '08': { name: 'Merchant Account Information', description: '商户账户信息' },
      '09': { name: 'Merchant Account Information', description: '商户账户信息' },
      '10': { name: 'Merchant Account Information', description: '商户账户信息' },
      '11': { name: 'Merchant Account Information', description: '商户账户信息' },
      '12': { name: 'Merchant Account Information', description: '商户账户信息' },
      '13': { name: 'Merchant Account Information', description: '商户账户信息' },
      '14': { name: 'Merchant Account Information', description: '商户账户信息' },
      '15': { name: 'Merchant Account Information', description: '商户账户信息' },
      '16': { name: 'Merchant Account Information', description: '商户账户信息' },
      '17': { name: 'Merchant Account Information', description: '商户账户信息' },
      '18': { name: 'Merchant Account Information', description: '商户账户信息' },
      '19': { name: 'Merchant Account Information', description: '商户账户信息' },
      '20': { name: 'Merchant Account Information', description: '商户账户信息' },
      '21': { name: 'Merchant Account Information', description: '商户账户信息' },
      '22': { name: 'Merchant Account Information', description: '商户账户信息' },
      '23': { name: 'Merchant Account Information', description: '商户账户信息' },
      '24': { name: 'Merchant Account Information', description: '商户账户信息' },
      '25': { name: 'Merchant Account Information', description: '商户账户信息' },
      '26': { name: 'Merchant Account Information', description: '商户账户信息' },
      '27': { name: 'Merchant Account Information', description: '商户账户信息' },
      '28': { name: 'Merchant Account Information', description: '商户账户信息' },
      '29': { name: 'Merchant Account Information', description: '商户账户信息' },
      '30': { name: 'Merchant Account Information', description: '商户账户信息' },
      '31': { name: 'Merchant Account Information', description: '商户账户信息' },
      '32': { name: 'Merchant Account Information', description: '商户账户信息' },
      '33': { name: 'Merchant Account Information', description: '商户账户信息' },
      '34': { name: 'Merchant Account Information', description: '商户账户信息' },
      '35': { name: 'Merchant Account Information', description: '商户账户信息' },
      '36': { name: 'Merchant Account Information', description: '商户账户信息' },
      '37': { name: 'Merchant Account Information', description: '商户账户信息' },
      '38': { name: 'Merchant Account Information', description: '商户账户信息' },
      '39': { name: 'Merchant Account Information', description: '商户账户信息' },
      '40': { name: 'Merchant Account Information', description: '商户账户信息' },
      '41': { name: 'Merchant Account Information', description: '商户账户信息' },
      '42': { name: 'Merchant Account Information', description: '商户账户信息' },
      '43': { name: 'Merchant Account Information', description: '商户账户信息' },
      '44': { name: 'Merchant Account Information', description: '商户账户信息' },
      '45': { name: 'Merchant Account Information', description: '商户账户信息' },
      '46': { name: 'Merchant Account Information', description: '商户账户信息' },
      '47': { name: 'Merchant Account Information', description: '商户账户信息' },
      '48': { name: 'Merchant Account Information', description: '商户账户信息' },
      '49': { name: 'Merchant Account Information', description: '商户账户信息' },
      '50': { name: 'Merchant Account Information', description: '商户账户信息' },
      '51': { name: 'Merchant Account Information', description: '商户账户信息' },
      '52': { name: 'Merchant Category Code', description: '商户类别代码' },
      '53': { name: 'Transaction Currency', description: '交易货币' },
      '54': { name: 'Transaction Amount', description: '交易金额' },
      '55': { name: 'Tip or Convenience Indicator', description: '小费或便利指示符' },
      '56': { name: 'Value of Convenience Fee Fixed', description: '固定便利费数值' },
      '57': { name: 'Value of Convenience Fee Percentage', description: '便利费百分比数值' },
      '58': { name: 'Country Code', description: '国家代码' },
      '59': { name: 'Merchant Name', description: '商户名称' },
      '60': { name: 'Merchant City', description: '商户城市' },
      '61': { name: 'Postal Code', description: '邮政编码' },
      '62': { name: 'Additional Data Field Template', description: '附加数据字段模板' },
      '63': { name: 'CRC', description: '循环冗余校验' },
      '64': { name: 'Merchant Information', description: '商户信息' },
      '65': { name: 'Merchant Information', description: '商户信息' },
      '66': { name: 'Merchant Information', description: '商户信息' },
      '67': { name: 'Merchant Information', description: '商户信息' },
      '68': { name: 'Merchant Information', description: '商户信息' },
      '69': { name: 'Merchant Information', description: '商户信息' },
      '70': { name: 'Merchant Information', description: '商户信息' },
      '71': { name: 'Merchant Information', description: '商户信息' },
      '72': { name: 'Merchant Information', description: '商户信息' },
      '73': { name: 'Merchant Information', description: '商户信息' },
      '74': { name: 'Merchant Information', description: '商户信息' },
      '75': { name: 'Merchant Information', description: '商户信息' },
      '76': { name: 'Merchant Information', description: '商户信息' },
      '77': { name: 'Merchant Information', description: '商户信息' },
      '78': { name: 'Merchant Information', description: '商户信息' },
      '79': { name: 'Merchant Information', description: '商户信息' },
      '80': { name: 'Unreserved Templates', description: '未保留模板' },
      '81': { name: 'Unreserved Templates', description: '未保留模板' },
      '82': { name: 'Unreserved Templates', description: '未保留模板' },
      '83': { name: 'Unreserved Templates', description: '未保留模板' },
      '84': { name: 'Unreserved Templates', description: '未保留模板' },
      '85': { name: 'Unreserved Templates', description: '未保留模板' },
      '86': { name: 'Unreserved Templates', description: '未保留模板' },
      '87': { name: 'Unreserved Templates', description: '未保留模板' },
      '88': { name: 'Unreserved Templates', description: '未保留模板' },
      '89': { name: 'Unreserved Templates', description: '未保留模板' },
      '90': { name: 'Unreserved Templates', description: '未保留模板' },
      '91': { name: 'Unreserved Templates', description: '未保留模板' },
      '92': { name: 'Unreserved Templates', description: '未保留模板' },
      '93': { name: 'Unreserved Templates', description: '未保留模板' },
      '94': { name: 'Unreserved Templates', description: '未保留模板' },
      '95': { name: 'Unreserved Templates', description: '未保留模板' },
      '96': { name: 'Unreserved Templates', description: '未保留模板' },
      '97': { name: 'Unreserved Templates', description: '未保留模板' },
      '98': { name: 'Unreserved Templates', description: '未保留模板' },
      '99': { name: 'Unreserved Templates', description: '未保留模板' },
      
      // 附加数据字段子标签 (Tag 62) - 使用不同的键名前缀
      '62_01': { name: 'Bill Number', description: '账单号码' },
      '62_02': { name: 'Mobile Number', description: '手机号码' },
      '62_03': { name: 'Store Label', description: '商店标签' },
      '62_04': { name: 'Loyalty Number', description: '忠诚度号码' },
      '62_05': { name: 'Reference Label', description: '参考标签' },
      '62_06': { name: 'Customer Label', description: '客户标签' },
      '62_07': { name: 'Terminal Label', description: '终端标签' },
      '62_08': { name: 'Purpose of Transaction', description: '交易目的' },
      '62_09': { name: 'Additional Consumer Data Request', description: '附加消费者数据请求' }
    };
  }

  // 检查是否为EMV码
  isEMVCode(data) {
    // EMV QR码通常以特定模式开始
    if (typeof data !== 'string') return false;
    
    // 检查是否符合EMV QR码格式
    // EMV QR码通常以'00'开头，后跟长度和值
    return /^00[0-9]{2}[0-9]{2}/.test(data) || 
           /^[0-9]{4}[0-9A-Fa-f]+/.test(data);
  }

  // 解析TLV数据
  parseTLV(data) {
    const result = [];
    let position = 0;
    
    while (position < data.length) {
      // 读取Tag (2字节)
      if (position + 2 > data.length) break;
      const tag = data.substring(position, position + 2);
      position += 2;
      
      // 读取Length (2字节)
      if (position + 2 > data.length) break;
      const lengthStr = data.substring(position, position + 2);
      const length = parseInt(lengthStr, 10);
      position += 2;
      
      // 读取Value
      if (position + length > data.length) break;
      const value = data.substring(position, position + length);
      position += length;
      
      // 获取标签定义
      const tagDef = this.tagDefinitions[tag] || {
        name: `Unknown Tag (${tag})`,
        description: `未知标签 (${tag})`
      };
      
      const tlvItem = {
        tag: tag,
        length: length,
        value: value,
        name: tagDef.name,
        description: tagDef.description,
        displayValue: this.formatValue(tag, value)
      };
      
      // 如果是模板标签，递归解析
      if (this.isTemplateTag(tag)) {
        tlvItem.subTags = this.parseTLV(value);
      }
      
      result.push(tlvItem);
    }
    
    return result;
  }

      // 检查是否为模板标签
    isTemplateTag(tag) {
      const templateTags = ['62', '64', '65', '66', '67', '68', '69', '70', '71', '72', '73', '74', '75', '76', '77', '78', '79'];
      return templateTags.includes(tag) || (parseInt(tag, 10) >= 2 && parseInt(tag, 10) <= 51);
    }

  // 格式化值显示
  formatValue(tag, value) {
    switch (tag) {
      case '00':
        return `版本 ${value}`;
      case '01':
        return value === '11' ? '静态' : value === '12' ? '动态' : value;
      case '52':
        return `MCC: ${value}`;
      case '53':
        return this.getCurrencyName(value);
      case '54':
        return `${value} (分)`;
      case '55':
        return value === '01' ? '提示输入小费' : value === '02' ? '便利费固定' : value === '03' ? '便利费百分比' : value;
      case '58':
        return this.getCountryName(value);
      case '59':
      case '60':
      case '61':
        return value;
      case '63':
        return `CRC: ${value}`;
      default:
        return value;
    }
  }

  // 获取货币名称
  getCurrencyName(code) {
    const currencies = {
      '156': 'CNY (人民币)',
      '840': 'USD (美元)',
      '978': 'EUR (欧元)',
      '392': 'JPY (日元)',
      '826': 'GBP (英镑)',
      '036': 'AUD (澳元)',
      '124': 'CAD (加元)',
      '702': 'SGD (新加坡元)',
      '344': 'HKD (港币)',
      '901': 'TWD (新台币)'
    };
    return currencies[code] || `货币代码: ${code}`;
  }

  // 获取国家名称
  getCountryName(code) {
    const countries = {
      'CN': '中国',
      'US': '美国',
      'JP': '日本',
      'GB': '英国',
      'DE': '德国',
      'FR': '法国',
      'AU': '澳大利亚',
      'CA': '加拿大',
      'SG': '新加坡',
      'HK': '香港',
      'TW': '台湾'
    };
    return countries[code] || `国家代码: ${code}`;
  }

  // 计算CRC
  calculateCRC(data) {
    // 移除现有的CRC字段
    const dataWithoutCRC = data.replace(/63[0-9]{2}[0-9A-Fa-f]{4}$/, '');
    
    // 简化的CRC-16计算 (实际EMV使用CRC-16 CCITT)
    let crc = 0xFFFF;
    for (let i = 0; i < dataWithoutCRC.length; i++) {
      crc ^= dataWithoutCRC.charCodeAt(i) << 8;
      for (let j = 0; j < 8; j++) {
        if (crc & 0x8000) {
          crc = (crc << 1) ^ 0x1021;
        } else {
          crc = crc << 1;
        }
      }
    }
    return (crc & 0xFFFF).toString(16).toUpperCase().padStart(4, '0');
  }

  // 验证CRC
  validateCRC(data) {
    const crcMatch = data.match(/63[0-9]{2}([0-9A-Fa-f]{4})$/);
    if (!crcMatch) return { valid: false, reason: '未找到CRC字段' };
    
    const providedCRC = crcMatch[1];
    const calculatedCRC = this.calculateCRC(data);
    
    return {
      valid: providedCRC === calculatedCRC,
      provided: providedCRC,
      calculated: calculatedCRC,
      reason: providedCRC === calculatedCRC ? 'CRC验证通过' : 'CRC验证失败'
    };
  }

  // 主解析方法
  parse(data) {
    if (!this.isEMVCode(data)) {
      return null;
    }

    const tlvData = this.parseTLV(data);
    const crcValidation = this.validateCRC(data);

    return {
      isEMV: true,
      rawData: data,
      tlvData: tlvData,
      crcValidation: crcValidation,
      summary: this.generateSummary(tlvData)
    };
  }

  // 生成摘要信息
  generateSummary(tlvData) {
    const summary = {};
    
    tlvData.forEach(item => {
      switch (item.tag) {
        case '59':
          summary.merchantName = item.value;
          break;
        case '60':
          summary.merchantCity = item.value;
          break;
        case '54':
          summary.amount = item.value;
          break;
        case '53':
          summary.currency = item.displayValue;
          break;
        case '52':
          summary.mcc = item.value;
          break;
        default:
          // 其他标签不处理
          break;
      }
    });
    
    return summary;
  }
}

export default EMVTLVParser;