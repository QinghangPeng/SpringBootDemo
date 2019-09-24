db.getCollection("qm_sta_dp_summary_data").aggregate(

	// Pipeline
	[
		// Stage 1
		{
			$match: {
				staDate:"2019-09-03"
			}
		},

		// Stage 2
		{
			$project: {
			    // specifications
			    mileageSum:{
			    	"$divide":["$mileageSum",100000]
			    }
			}
		},

		// Stage 3
		{
			$group: {
				_id:0,
				k1:{
					"$sum":{
						"$cond":[{"$lt":["$mileageSum",20]},1,0]
					}
				},
				k2:{
					"$sum":{
						"$cond":[{"$and":[{"$gte":["$mileageSum",20]},{"$lt":["$mileageSum",40]}]},1,0]
					}
				},
				k3:{
					"$sum":{
						"$cond":[{"$and":[{"$gte":["$mileageSum",40]},{"$lt":["$mileageSum",60]}]},1,0]
					}
				},
				k4:{
					"$sum":{
						"$cond":[{"$gte":["$mileageSum",60]},1,0]
					}
				}
			}
		},

		// Stage 4
		{
			$match: {
			
			}
		},

		// Stage 5
		{
			$match: {
			
			}
		},

	]

	// Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

);
