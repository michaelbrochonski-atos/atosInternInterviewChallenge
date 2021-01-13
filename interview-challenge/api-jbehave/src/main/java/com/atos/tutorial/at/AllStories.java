
package com.atos.tutorial.at;

import java.util.List;

import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wipro.tutorial.at.configuration.AbstractStoryConfiguration;
import com.wipro.tutorial.at.configuration.ProjectConfiguration;

public class AllStories extends AbstractStoryConfiguration {

	@Override
	public ApplicationContext getAnnotatedApplicationContext() {
		return new AnnotationConfigApplicationContext(ProjectConfiguration.class);
	}
	
	@Override
	public List<String> storyPaths() {
		List<String> paths = new StoryFinder().findPaths(
				CodeLocations.codeLocationFromClass(this.getClass()), "**/stories/Hero.story", "");
		return paths;
	}
}
