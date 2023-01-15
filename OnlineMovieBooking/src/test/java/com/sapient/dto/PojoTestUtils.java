package com.sapient.dto;
import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import  pl.pojo.tester.api.assertion.Method;

public class PojoTestUtils {

	
	public static void validatePojoClasses(Class<?> classUnderTest, boolean quickTestEnabled, String... excludedFields) {
		if(quickTestEnabled) {
			assertPojoMethodsFor(classUnderTest, exclude(excludedFields))
								.testing(Method.GETTER, Method.SETTER)
								.testing(Method.TO_STRING).testing(Method.EQUALS).testing(Method.HASH_CODE)
								.testing(Method.CONSTRUCTOR).quickly().areWellImplemented();
		}else {
			assertPojoMethodsFor(classUnderTest, exclude(excludedFields))
								.testing(Method.GETTER, Method.SETTER)
								.testing(Method.TO_STRING).testing(Method.EQUALS).testing(Method.HASH_CODE)
								.testing(Method.CONSTRUCTOR).areWellImplemented();
		}
	}

	
}
