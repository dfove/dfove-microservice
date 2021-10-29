package com.microservice.fastdfs.kafka;

public interface DataCleaningRB {

	public void onDataRb(DataCleaningType dataCleaningType);

	/**
	 * 行为处理
	 * @param dataCleaningType
	 */
	public void onDataBehavior(DataCleaningType dataCleaningType);

	/**
	 * 关系处理
	 * @param dataCleaningType
	 */
	public void onDataRelation(DataCleaningType dataCleaningType);

}
