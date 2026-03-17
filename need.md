需要添加到后端的函数
/**针对单个工序分配采购负责人
 * @param workUnique 工程单唯一索引 (work_id + "_" + work_ver)
 * @param iimID：该工单中 intermedia 数组中的序号 (即 idx)，用来索引到对应工序
 * @param head 负责人姓名 (head_pur)
 * 作用：找到work_unique==workUnique的工单，
 再找到其intermedia数组的第iimID个元素，将head赋值给head_PUR。
 */
export const AddHead_Pur = (workUnique: string, iimID: number, head: string) => {
  return service.post('/workOrders/addHeadPur', {
    work_unique: workUnique,
    intermediaID: iimID, // 对应你前端传入的 idx,是intermedia的序号
    head_PUR: head,
  })
}

/*针对单个工序分配外发负责人
作用：找到work_unique==workUnique的工单，
再找到其intermedia数组的第iimID个元素(即工序），将head赋值给head_OUT。
 */
export const AddHead_Out = (workUnique: string, iimID: number, head: string) => {
  return service.post('/workOrders/addHeadOut', {
    work_unique: workUnique,
    intermediaID: iimID, // 对应你前端传入的 idx,是intermedia的序号
    head_OUT: head,
  })
}

//针对整个工单分配生产装订负责人
/*作用：找到work_unique==workUnique的工单，
将head赋值给head_MNF，默认生产任务只有一个工序所以只有一个负责人。
 */
export const AddHead_Mnf = (workUnique: string, head: string) => {
  return service.post('/workOrders/addHeadMnf', {
    work_unique: workUnique,
    head_MNF: head,
  })
}

/**向云端同步采购进度
 * @param workUnique 工程单唯一索引 (work_id + "_" + work_ver)
 * @param iimID：该工单中 intermedia 数组中的序号 (即 idx)，用来索引到对应工序
 * @param progress: 进度，将会对应数据库里的yiGouJianShu 已购件数
 * 作用：找到work_unique==workUnique的工单，
 再找到其intermedia数组的第iimID个元素工序，将progress赋值给yiGouJianShu。
 */
export const UpdateProgress_Pur = (workUnique: string, iimID: number, progress: number) => {
  return (
    service.post('/workOrders/updateProgressPur'),
    {
      work_unique: workUnique,
      intermediaID: iimID,
      yiGouJianShu: progress,
    }
  )
}

/**向云端同步采购进度
 * @param workUnique 工程单唯一索引 (work_id + "_" + work_ver)
 * @param iimID：该工单中 intermedia 数组中的序号 (即 idx)，用来索引到对应工序
  * @param start：外发工序开始时间
 * @param end: 预期外发工序结束时间
 * @param progress: 进度，将会对应数据库里的dangQianJinDu
 * 作用：找到work_unique==workUnique的工单，
 再找到其intermedia数组的第iimID个元素工序，将progress赋值给dangQianJinDu。
 */
export const UpdateProgress_Out = (
  workUnique: string,
  iimID: number,
  start: string,
  end: string,
  progress: number,
) => {
  return (
    service.post('/workOrders/updateProgressOut'),
    {
      work_unique: workUnique,
      intermediaID: iimID,
      kaiShiRiQi: start, //工序开始日期
      yuQiJieShu: end, //工序预期结束日期
      dangQianJinDu: progress,
    }
  )
}


/**向云端同步生产包装进度
 * @param workUnique 工程单唯一索引 (work_id + "_" + work_ver)
 * @param progress 进度，将会对应工单里的zhuangDingJianShu装订件数
 */
export const UpdateProgress_Mnf = (workUnique: string, progress: number) => {
  return (
    service.post('/workOrders/updateProgressMnf'),
    {
      work_unique: workUnique,
      zhuangDingJianShu: progress,
    }
  )
}