package ibf2023.csf.backend.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Date;
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
		let filter = new Date();
		filter.setDate(1)
		filter.setHours(0,0,0,0)

		db.travelpics.aggregate([
			{$match : {date: {$gte: filter}}},
			{$group: 
				{
				_id: { },
				totalSize: { $sum: "$size" } 
				}
			}
		])
	 */
	@SuppressWarnings("deprecation")
	public Long getCurrentSize() {
		Date filter = new Date();
		filter.setDate(1);
		filter.setHours(0);
		filter.setMinutes(0);
		filter.setSeconds(0);
		System.out.println(">>> filter: " + filter);

		MatchOperation match = Aggregation.match(Criteria.where("date").gte(filter));

		GroupOperation group = Aggregation.group()
					.sum("size").as("totalSize");
					
		Aggregation pipeline= Aggregation.newAggregation(match, group);
		AggregationResults<Document> results= mongoTemplate.aggregate(pipeline, "travelpics", Document.class);
		List<Document> docList = results.getMappedResults();
		return docList.get(0).getLong("totalSize");
	}

}
