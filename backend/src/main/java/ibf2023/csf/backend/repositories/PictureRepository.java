package ibf2023.csf.backend.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ibf2023.csf.backend.models.UploadPicture;

@Repository
public class PictureRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// Task 4.2
	// You may change the method signature by adding parameters and/or the  return type
	// You may throw any exception 

	/*
		db.travelpics.insert({
			date: ISODate("2024-06-14T04:24:15.000+0000"),
			title: "This is my title",
			comments: "This is my comment",
			url: "www.url.com",
			"size": 123456
		});
	 */
	public UploadPicture save(UploadPicture pic) {
		// IMPORTANT: Write the native mongo query in the comments above this method
        return mongoTemplate.insert(pic, "travelpics");
	}


	/*
		db.travelpics.aggregate([
			{$group: 
				{
					_id: { },
					totalSize: { $sum: "$size" } 
				}
			}
		])
	 */
	public Long getCurrentSize() {
		// TODO: fix it to get data for CURRENT MONTH only
		GroupOperation group = Aggregation.group()
					.sum("size").as("totalSize");
					
		Aggregation pipeline= Aggregation.newAggregation(group);
		AggregationResults<Document> results= mongoTemplate.aggregate(pipeline, "travelpics", Document.class);
		List<Document> docList = results.getMappedResults();
		return docList.get(0).getLong("totalSize");
	}

}
