package com.java.project;

import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	
	import java.util.ArrayList;
	import java.util.List;

public class SurveySystem extends JFrame {
	    private List<Survey> surveys;

	    public SurveySystem() {
	        surveys = new ArrayList<>();
	        setTitle("Online Survey System");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 300);
	        setLocationRelativeTo(null);

	        JButton createSurveyButton = new JButton("Create Survey");
	        createSurveyButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                createSurvey();
	            }
	        });

	        JPanel panel = new JPanel();
	        panel.add(createSurveyButton);
	        add(panel, BorderLayout.CENTER);
	    }

	    private void createSurvey() {
	        String surveyName = JOptionPane.showInputDialog("Enter survey name:");
	        if (surveyName != null && !surveyName.isEmpty()) {
	            Survey survey = new Survey(surveyName);
	            surveys.add(survey);

	            // Display the survey questions
	            survey.displaySurvey();
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new SurveySystem().setVisible(true);
	            }
	        });
	    }
	}

	class Survey {
	    private String name;
	    private List<String> questions;

	    public Survey(String name) {
	        this.name = name;
	        questions = new ArrayList<>();
	    }

	    public void addQuestion(String question) {
	        questions.add(question);
	    }

	    public void displaySurvey() {
	        StringBuilder surveyString = new StringBuilder();
	        surveyString.append("Survey: ").append(name).append("\n");
	        for (int i = 0; i < questions.size(); i++) {
	            surveyString.append(i + 1).append(". ").append(questions.get(i)).append("\n");
	        }
	        JOptionPane.showMessageDialog(null, surveyString.toString());
	    }
	}
