package mongo;

import com.alibaba.fastjson.JSON;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MorphiaTest {

	public static void main(String[] args) {
		MorphiaTest test = new MorphiaTest();
		try {
			test.run();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void run() throws UnknownHostException {
		MongoClient mongo = new MongoClient("123.59.110.201", 27018);
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongo, "herol");
		Iterable<Employee> it = datastore.createQuery(Employee.class).fetch();
		while (it.iterator().hasNext()) {
			System.out.println("fetch: " + it.iterator().next().toString());
		}
		Employee e = new Employee();
		e.setName("hjc");
		Employee e2 = new Employee();
		e2.id = "e2";
		e2.setName("hjc2");
		e.getDirectReports().add(e2);
		System.out.println(morphia.toDBObject(e));
		datastore.save(e);
	}
}

@Entity("employees")
@Indexes(@Index(value = "salary", fields = @Field("salary")))
class Employee {
	@Id
	public String id;
	private String name;
	private Integer age;
	// @Embedded
	private Employee manager;
	// @Embedded
	private List<Employee> directReports = new ArrayList<Employee>();
	@Property("wage")
	private Double salary;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getDirectReports() {
		return directReports;
	}

	public void setDirectReports(List<Employee> directReports) {
		this.directReports = directReports;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

}