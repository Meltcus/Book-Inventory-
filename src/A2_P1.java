import java.io.*;
import java.util.Scanner;
//-----------------------------------------------------
//Alvi Shariar Matin (40250537) & Peter Salameh (40228208)
//Comp 249
//Assignment #2 Part 1-2-3
//November 8 2023
//-----------------------------------------------------
// 		Description of Program	
// This program reads given txt files and ultimately creates an interface in which a user can interact to view the books.
//
// Firstly it reads every txt file line by line given in input_file_part1. It filters out all the lines with syntax error using the 4 syntax related 
//		Check_Exceptions method if it throws then a thay line gets appened to a a file with all the syntax erros. 
// 		If it is syntactically correct, it then gets classified into various .csv files, genre by genre,
// In part 2, it reads all of these .csv files that we created from part 1 and checks for semantic errors with the 4 new semantic related and if any of them
//  	throw an exception, then it gets caught and appened to a file named semantic erros similar to part 1. The good lines are then stored into a file
//		named GoodFiles.csv and then stored into a Book[] array, Genre by Genre. Finally the serilization of the book class allows each of these
//		book objects to be written into binary files (.csv.ser) genre by genre
// Finally in part 3, We have a display menu allowing the user to pick and view whichver file they would like through a very simple interface.
//


public class A2_P1 {

	public static void main(String[] args) {
		
		// If The TA wants to delete the files, tell the TA that we have a Reset Files methods that will reset all of the used files instead of delete it
		
		do_part1();
		
		do_part2();

		do_part3();
		
		
	}
	
	
	// Part 1 Methods

/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/		
	
	/**
	 * This method reads each line of code from p1_input_files - Skips the line with #16. It uses the SeperatingCSVFiles method to go in depth of each file
	 * 
	 * @param part1_input_file_names is a File that will be read
	 */
	public static void Read_part1_input_file_names(String part1_input_file_names) {
		
		try {
			
			BufferedReader Reader = new BufferedReader(new FileReader(part1_input_file_names));
			
			String Line;
			
			// Reads 1 file from the files list
			
			Reader.readLine(); // Ignores the #16
			
			while ( (Line = Reader.readLine()) != null) {
				
				separatingCSV(Line);

			 }
		
			
			Reader.close();
			
			
		}	
		catch (IOException e) {
		
			System.out.println("File "+ part1_input_file_names  + " could not be opened");
			return;
		}
		
		
	
	}
	

    /**
     * This method takes in a Book CSV.txt file and reads line by line and splits the content of each line into a String line whihc gets checked for exceptions
     * 
     * @param CSV_File taken from the lines of part1_input_file_names.txt
     */
    // Important Method
    public static void separatingCSV(String CSV_File) {

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_File))) {
            
        	String line, Genre;
        	
//        	0 - Title 	1 - Author	2 - Price 	3 - ISBN 	4 - Genre 	5 - Year
            
        	while ((line = br.readLine()) != null)
        	{
        		String[] CSVvalues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1); 
                
                // ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" 

        		CSVvalues[0] = CSVvalues[0].replaceAll("  ^\"  |  \"$  ", ""); // Remove quotes from title begining or from the end
        		
        		
        		// Now Check for exceptions
        		
        		// If the three exception methods are true, meaning they don't throw any method then we check if
        		//	genre is correct or not, in order to classify them in their proper files	
        		
        		if (Check_TooFewFieldsException (CSVvalues, CSV_File) && Check_TooManyFieldsException (CSVvalues, CSV_File) && Check_MissingFieldException (CSVvalues, CSV_File) )
        			if ( 	!(Genre = Check_UnknownGenre(CSVvalues, CSV_File)) . equals("NONE")		)
        				Classify_Genre(CSVvalues);
        		
        		          
        	}
        	
        } catch (IOException e) {
            System.out.println("Inner File could not be opened");
        }
    }
    
    
    // Catching the exceptions and adding to syntax_error_file.txt if neccesary 

    
    // All Exception Cases
    
    /**
     * This method checks if theres too few fields and if so then it appends to a file called Syntax_Error_File.txt
     * 
     * @param A an array thats gets checked if too little
     * @param CSVFile to display which file is being checked
     */
    public static boolean Check_TooFewFieldsException (String[] A, String CSVFile) {
    	
	    	try {
	    		
	    		if (A.length < 6)
	    		throw new TooFewFieldsException();
	    		
	    		else
	    			return true;
	    	
	    	} catch (TooFewFieldsException e) {
	    		
	    		// Make a new file thats appending to Syntax_Error_File.txt
	    		
	    		try 
	    		{
	    			
	    			BufferedWriter br = new BufferedWriter(new FileWriter("syntax_error_file.txt",true));
	    			
	    			br.append("Syntax error in file: " + CSVFile + "\n");
	    			   			
	    			br.append("====================\n\n");
	    			
	    			br.append("Error: Too Few Fields \nRecord: ");
	    			
	    			
	    			for (int i = 0 ; i < A.length ; i++) {
	    				
	    				br.append(A[i] + ", ");
	    				
	    			}
	    			
	    			br.append("\n\n");
	    			
	    			
	    			br.close();
	    			
	    			return false;
	    			
	    			
	    		
	    		} catch (IOException e1) {
	                
	    			System.out.println("Syntax File could not be opened");
	    		
	    			return false;
	    			
	            }
	    		
	    		
	    	}   	
    }

    
    /**
     * This method checks if theres too many fields and if so then it appends to a file called Syntax_Error_File.txt
     * 
     * @param A an array thats gets checked if too many
     * @param CSVFile to display which file is being checked
     */
    public static boolean Check_TooManyFieldsException (String[] A, String CSVFile) {
    	
    	try {
    		
    		if (A.length > 6)
    			throw new TooManyFieldsException();
    		else
    			return true;
    	
    	} catch (TooManyFieldsException e) {
  		
    		// Make a new file thats appending to Syntax_Error_File.txt
    		
    		try 
    		{
    			
    			BufferedWriter br = new BufferedWriter(new FileWriter("syntax_error_file.txt",true));
    			
    			br.append("Syntax error in file: " + CSVFile + "\n");
    			   			
    			br.append("====================\n\n");
    			
    			br.append("Error: Too Many Fields \nRecord: ");
    			
    			
    			for (int i = 0 ; i < A.length ; i++) {
    				
    				br.append(A[i] + ", ");
    				
    			}
    			
    			br.append("\n\n");
    			
    			
    			br.close();
    			
    			return false;
    			
    			
    		
    		} catch (IOException e1) {
                
    			System.out.println("Syntax File could not be opened");
    			
    			return false;
    			
            }
    	
    	
    	
    	}
    	
    	
    }
    
    
    /**
     * This method checks if theres the entered Genre is a valid Genre and if not then it appends to a file called Syntax_Error_File.txt and if so then the Classify_Genre method is invoked
     * 
     * @param A an array thats gets checked if valid genre has been entered
     * @param CSVFile to display which file is being checked
     */
    public static String Check_UnknownGenre (String[] A, String CSVFile) {
    	
    	String[] Genre = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};
    	
    	try {
	    	for (int i = 0 ; i < Genre.length ; i++) {
	    		if (A[4].equals(Genre[i]))
	    			return A[4];
	    	}
    	
	    	throw new UnknownGenreException();
    	
    	} catch (UnknownGenreException e) 
    	
    	{
    		// Make a new file thats appending to Syntax_Error_File.txt
    		
    		try 
    		{
    			
    			BufferedWriter br = new BufferedWriter(new FileWriter("syntax_error_file.txt",true));
    			
    			br.append("Syntax error in file: " + CSVFile + "\n");
    			   			
    			br.append("====================\n\n");
    			
    			br.append("Error: Unknown Genre Exception \nRecord: ");
    			
    			
    			for (int i = 0 ; i < A.length ; i++) {
    				
    				br.append(A[i] + ", ");
    				
    			}
    			
    			br.append("\n\n");
    			
    			
    			br.close();
    			
    			
    		
    		} catch (IOException e1) {
                
    			System.out.println("Syntax File could not be opened");
    			
            }
    		
    		return "NONE";
    		
    	}
    	
    }

    /**
     * This method checks if theres a missing field and if so then it checks which element is missing and then appends to a file called Syntax_Error_File.txt calling out the missing element
     * 
     * @param A an array thats gets checked for any empty elements vis .isEmpty()
     * @param CSVFile to display which file is being checked
     */
    public static boolean Check_MissingFieldException (String[] A, String CSVFile) {
    	
    	try {

//			0 - Title 	1 - Author	2 - Price 	3 - ISBN 	4 - Genre 	5 - Year   		
    		
    		for (int i = 0 ; i < A.length ; i++) {
    			
    			if (A[i].isEmpty()) {
    					
    				BufferedWriter br = new BufferedWriter(new FileWriter("syntax_error_file.txt",true));
    				

    				switch (i) {
    				  
    				case 0: // Missing Title
    				    	
    				   	br.append("Syntax error in file: " + CSVFile + "\n");
    		    			
    		    		br.append("====================\n\n");
    		    			
    		   			br.append("Error: Missing Title \nRecord: ");
    		    			
    		    			
    		    			
    		   			for (int j = 0 ; j < A.length ; j++) {
    		    				
    		   				br.append(A[j] + ", ");
    		   				
    	    			}
    		    			
   		    			br.append("\n\n");
    		    			
   		    			br.close();
    		    			
   				    	break;
    				    
    				    
    				case 1: // Missing Author
    				        
    			    	br.append("Syntax error in file: " + CSVFile + "\n");
    		    			
    		   			br.append("====================\n\n");
    		    			
    		   			br.append("Error: Missing Author \nRecord: ");
    		    			
    		    			
    		    			
    		   			for (int j = 0 ; j < A.length ; j++) {
    		    				
    		   				br.append(A[j] + ", ");
    		    				
    	    			}
    		    			
   		    			br.append("\n\n");
    		    			
   		    			br.close();
    				    	
   				    	break;
    				    
    				    
    				case 2: // Missing Price
    				    	
    				    br.append("Syntax error in file: " + CSVFile + "\n");
    		    			
    		    		br.append("====================\n\n");
    		    			
    		    		br.append("Error: Missing Price \nRecord: ");
    		    			
    		    			
    		    			
    		    		for (int j = 0 ; j < A.length ; j++) {
    		   				
    		   				br.append(A[j] + ", ");
    		   				
    	    			}
    		    			
    	    			br.append("\n\n");
   		    			
   		    			br.close();
   				       
   				    	break;
    				   	   
    				
    				case 3: // Missing ISBN
    				    
				    	br.append("Syntax error in file: " + CSVFile + "\n");
		    			
		    			br.append("====================\n\n");
		    			
		    			br.append("Error: Missing ISBN \nRecord: ");
		    			
		    			
		    			
		    			for (int j = 0 ; j < A.length ; j++) {
		    				
		    				br.append(A[j] + ", ");
		    				
		    			}
		    			
		    			br.append("\n\n");
		    			
		    			br.close();
				       
				    	break;
    					

    				    	  
    				case 4: // Missing Genre
    				        
				    	br.append("Syntax error in file: " + CSVFile + "\n");
		    			
		    			br.append("====================\n\n");
		    			
		    			br.append("Error: Missing Genre \nRecord: ");
		    			
		    			
		    			
		    			for (int j = 0 ; j < A.length ; j++) {
		    				
		    				br.append(A[j] + ", ");
		    				
		    			}
		    			
		    			br.append("\n\n");
		    			
		    			br.close();
    					
    				    break;
  
    				  
    				case 5: // Missing Year
    				    
				    	br.append("Syntax error in file: " + CSVFile + "\n");
		    			
		    			br.append("====================\n\n");
		    			
		    			br.append("Error: Missing Year \nRecord: ");
		    			
		    			
		    			
		    			for (int j = 0 ; j < A.length ; j++) {
		    				
		    				br.append(A[j] + ", ");
		    				
		    			}
		    			
		    			br.append("\n\n");
		    			
		    			br.close();
    					
    				    break;
    				    
    				    
    				    default:
    				        break;
    				
    				} // End of Switch Case

    				
    				br.close();
    				
    				
    				throw new MissingFieldException();
    				

    			
    			} // End of If Statement
    		
    		} // End of For-Loop
    		
    		return true;
    		
    	} 
    	
    	catch (IOException e1) {
    		System.out.println("syntax_error_file.txt could not be opened");
    	}
    	
    	
    	catch (MissingFieldException e)
    	{
			return false;
    	}
    	
    	return true;
    	
    }
    
    
    // Create Genre File
    
    /**
     * For the case where there is a valid genre entry - This method classifies each genre into its own CSV file
     * @param A is String array to check which Genre has been entered
     */
    public static void Classify_Genre (String[] A) {
    
    	String[] Genre = {"CCB", "HCB", "MTV", "MRB", "NEB", "OTR", "SSM", "TPA"};

    	try {
    	
    		switch (A[4]) {
        
		    	case "CCB":
		    		
		    		BufferedWriter CCB = new BufferedWriter (new FileWriter("Cartoons_Comics_Books.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
	    				CCB.append(A[i] + ", ");

		    		CCB.append("\n");
		    		
		    		CCB.close();

		    		break;
		       
		    	
		    	case "HCB":
		    		
		    		BufferedWriter HCB = new BufferedWriter (new FileWriter("Hobbies_Collectibles_Books.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
	    				HCB.append(A[i] + ", ");

		    		HCB.append("\n");
		    		
		    		HCB.close();
		        
		    		
		    		break;
		        
		    		
		    	case "MTV":
		        
		    		BufferedWriter MTV = new BufferedWriter (new FileWriter("Movies_TV.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			MTV.append(A[i] + ", ");

		    		MTV.append("\n");
		    		
		    		MTV.close();
		    		
		    		break;
		        
		    		
		    	case "MRB":
		    		
		    		BufferedWriter MRB = new BufferedWriter (new FileWriter("Music_Radio_Books.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			MRB.append(A[i] + ", ");

		    		MRB.append("\n");
		    		
		    		MRB.close();
		    		
		    		
		    		break;
		        
		    		
		    	case "NEB":
		        
		    		BufferedWriter NEB = new BufferedWriter (new FileWriter("Nostalgia_Eclectic_Books.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			NEB.append(A[i] + ", ");

		    		NEB.append("\n");
		    		
		    		NEB.close();
		    		
		    		break;
		        
		    		
		    	case "OTR":
		            
		    		BufferedWriter OTR = new BufferedWriter (new FileWriter("Old_Time_Radio.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			OTR.append(A[i] + ", ");

		    		OTR.append("\n");
		    		
		    		OTR.close();

		    		
		    		break;
		        
		    		
		    	case "SSM":
	
		    		BufferedWriter SSM = new BufferedWriter (new FileWriter("Sports_Sports_Memorabilia.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			SSM.append(A[i] + ", ");

		    		SSM.append("\n");
		    		
		    		SSM.close();
		    		
		    		
		    		break;
		        
		    		
		    	case "TPA":
		           
		    		BufferedWriter TPA = new BufferedWriter (new FileWriter("Trains_Planes_Automobiles.csv",true));
		            
		    		for (int i = 0 ; i < A.length ; i++) 
		    			TPA.append(A[i] + ", ");

		    		TPA.append("\n");
		    		
		    		TPA.close();
		    		
		    		
		    		break;
		        
		    		
		    	default:
		           
		    		break;
	    }
    	} catch (IOException e) {
    		
    		System.out.println("Could Not Open CSV file");
    		
    	}
    }
    
    
    // Reseting Files
    
    /**
     * This method resets any files to a blank file
     * 
     * @param File is a String of a file that will be resetted
     */
    public static void Reset_Files(String File) {
    	
    	// Reseting Files
		
    			try {
    				BufferedWriter FileReset = new BufferedWriter(new FileWriter(File));
    			
    				FileReset.write("");
    				
    				FileReset.close();
    				
    			} catch (IOException e) {
    				System.out.println("Cannot Open sytanx file to reset it");
    				System.exit(1);
    			}
    			
    	
    }
    
    /**
     * This method resets all the files used in part 1 - Used to properly test the files
     * @test
     */
    public static void Reset_All_Files() {
    	
		Reset_Files("syntax_error_file.txt"); 
		
		Reset_Files("Cartoons_Comics_Books.csv");
		Reset_Files("Hobbies_Collectibles_Books.csv");
		Reset_Files("Movies_TV.csv");
		Reset_Files("Music_Radio_Books.csv");
		Reset_Files("Nostalgia_Eclectic_Books.csv");
		Reset_Files("Old_Time_Radio.csv");
		Reset_Files("Sports_Sports_Memorabilia.csv");
		Reset_Files("Trains_Planes_Automobiles.csv");
		
		Reset_Files("semantic_error_file.txt");
    	Reset_Files("Good_File.csv");
    }
    
    // Reseting Files
    
    
    
    /**
     * This method does all the work of part 1 - it uses the Reset_All_Files method to clear all the created files and Read_part1_input_file_names
     * 	which creates files of syntax and genre types with the help of other methods used inside of it
     */
	public static void do_part1() {
		
		Reset_All_Files();

		Read_part1_input_file_names("part1_input_file_names.txt");
		
	}


	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/		

	// Part 2 Methods
	
	
//	0 - Title 	1 - Author	2 - Price 	3 - ISBN 	4 - Genre 	5 - Year   		 
	
   /** 
    * This method Reads the .csv file created from part 1 and then filters out the semantically errored files and stores the good lines into 
    * 	a Book array, genre by genre. Afterwards it uses the serilizable book class to then write it into a binary file (.csv.ser) which gets used in part 3
    * 
    */
	public static void do_part2() {
   	
	   Reset_Files("semantic_error_file.txt");
   	
	   Reset_Files("Good_File.csv");
	   
	   Reset_Files("Cartoons_Comics_Books.csv.ser");
       	
	   
	    Book[] Cartoons_Comics = null; 					// 7
	   
	   	Book[] Hobbies_Collectibles = null; 			// 7
	   	
	   	Book[] 	Movies_TV_Books = null; 				// 111
	   	
	   	Book[] Music_Radio_Books = null;				// 65	
	   	
	   	Book[] Nostalgia_Eclectic_Books = null; 		// 4
	   	
	   	Book[] 	Old_Time_Radio_Books = null; 			// 3
	   	
	   	Book[] 	Sports_Sports_Memorabilia = null;		// 27	
	   		
	   	Book[] Trains_Planes_Automobiles = null;		// 2
	   			
	   	
	   	
	   	Cartoons_Comics = Read_CSVFiles("Cartoons_Comics_Books.csv", Cartoons_Comics);
	   	
	   	Hobbies_Collectibles = Read_CSVFiles("Hobbies_Collectibles_Books.csv", Hobbies_Collectibles);
	   	
	   	Movies_TV_Books = Read_CSVFiles("Movies_TV.csv", Movies_TV_Books);
	   	
	   	Music_Radio_Books = Read_CSVFiles("Music_Radio_Books.csv", Music_Radio_Books);
	   	
	   	Nostalgia_Eclectic_Books = Read_CSVFiles("Nostalgia_Eclectic_Books.csv", Nostalgia_Eclectic_Books);
	   	
		Old_Time_Radio_Books = Read_CSVFiles("Old_Time_Radio.csv", Old_Time_Radio_Books);
	   	
		Sports_Sports_Memorabilia = Read_CSVFiles("Sports_Sports_Memorabilia.csv", Sports_Sports_Memorabilia);
	   	
		Trains_Planes_Automobiles = Read_CSVFiles("Trains_Planes_Automobiles.csv", Trains_Planes_Automobiles);

	   	
	   	CSV_to_Binary ("Cartoons_Comics_Books.csv.ser", Cartoons_Comics);
	   	
	   	CSV_to_Binary ("Hobbies_Collectibles_Books.csv.ser", Hobbies_Collectibles);

	   	CSV_to_Binary ("Movies_TV.csv.ser", Movies_TV_Books);
	   	
	   	CSV_to_Binary ("Music_Radio_Books.csv.ser", Music_Radio_Books);
	   	
	   	CSV_to_Binary ("Nostalgia_Eclectic_Books.csv.ser", Nostalgia_Eclectic_Books);
	   	
	   	CSV_to_Binary ("Old_Time_Radio.csv.ser", Old_Time_Radio_Books);
	   	
	   	CSV_to_Binary ("Sports_Sports_Memorabilia.csv.ser", Sports_Sports_Memorabilia);
	   	
	   	CSV_to_Binary ("Trains_Planes_Automobiles.csv.ser", Trains_Planes_Automobiles);
	   	
	  
   }
    
	/**
	 * This file reads from csv files created from part 1 and then checks for any semantic errors via the exception Check methods
	 * 
	 * @param CSVFile
	 * @param Genre
	 * @return
	 */
	public static Book[] Read_CSVFiles(String CSVFile, Book[] Genre) {
			
			try {
				
				Reset_Files("Good_File.csv");
				
				BufferedReader br = new BufferedReader ( new FileReader (CSVFile));
				
				BufferedWriter bw = new BufferedWriter (new FileWriter ("Good_File.csv",true));
				
				String Line;
				
				int ProperLine = 0; // How Many Proper Lines in a File
				
				
				
				while ( (Line = br.readLine()) != null) 
				
				{
					
					int Check = 0;
					
					String[] CSValues = Line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					
					// Removes any whitespaces at beginning of a String
					
					for (int i = 0 ; i < CSValues.length ; i++) {
						
						if (CSValues[i].charAt(0) == ' ')
							CSValues[i] = CSValues[i].substring(1);
						
					}
					
					
					if (CSValues[3].length() == 10)
						if (Check_ISBN10Exception(CSValues,CSVFile))  // if true then it is does not trigger the Exception
							Check++;
					
					
					if (CSValues[3].length() == 13)
						if (Check_ISBN13Exception(CSValues,CSVFile))
							Check++;
					
					
					if (Check_BadPriceException(CSValues,CSVFile))
						Check++;
					
					
					if(Check_BadYearException(CSValues,CSVFile))
						Check++;
					
					// Checing if a proepr line then writing to a file which we will read from to add to an object
					// This is done to get the proper length for each Book[] Genre by Genre
					
					if (Check == 3)  { 
						ProperLine++;
						
						for ( int i = 0 ; i < CSValues.length; i++) {
							bw.append(CSValues[i]);
							
							if ( i < CSValues.length - 1)
								bw.append(", ");
						}
						
						bw.append("\n");
						
						
					}
				
				} // End of Loop
				
				
				br.close();
				
				bw.close();
				
				
				// Adding all the Good Lines from file Good_File.csv to the Book Genre Array
				
				Genre = new Book[ProperLine];
				
				
				double Price;
				   
				double Year;
				
				int j = 0;
				
				// Reading from the Good_File so we can store it in the Book array
				
				try {
					
					BufferedReader ibr = new BufferedReader (new FileReader ("Good_File.csv"));
					
					
					while ( (Line = ibr.readLine()) != null) {
						
						
						
						String [] Inner_CSValues = Line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
						   	
						for (int i = 0 ; i < Inner_CSValues.length ; i++) 
						{
								
							if (Inner_CSValues[i].charAt(0) == ' ')
								Inner_CSValues[i] = Inner_CSValues[i].substring(1);
						}	
						
						   
						Price = Double.parseDouble(Inner_CSValues[2]);
						   
						Year = Integer.parseInt(Inner_CSValues[5]);
						   
						Genre[j] = new Book (Inner_CSValues[0],Inner_CSValues[1],Price,Inner_CSValues[3], Inner_CSValues[4], (int) Year);					
					
						j++;
						
					} // end of file loop
					
					ibr.close();
					
				} catch (IOException e) {
					
					System.out.println("Could not open Inner File");
					
				}				
						
			} catch (IOException e) {
				System.out.println("Could not open/create file ");
			}
			
			
			if (Genre == null) {
				System.out.println(Genre + " is empty");
				return Genre;
			}
			
			else
				return Genre;
			
			
		}
		
	/**
	 * This method takes in an Array of Books and a .csv.ser file and then it writes the objects from the Book arrray into that file
	 * 
	 * @param File is a String and a output for the objects as .csv.ser
	 * @param Genre is a Book Array
	 */
	public static void CSV_to_Binary(String File, Book[] Genre) {
		  
		  ObjectOutputStream oos = null;
		  
		  try {
			  
			  oos = new ObjectOutputStream (new FileOutputStream (File) ) ;
			  
			  
			  for (int i = 0 ; i < Genre.length ; i++) {
				  oos.writeObject(Genre[i]);
			  }
		  
			  
			  oos.close();
		 
			  
		  } 
		  
		  catch (EOFException e) {
			  
			  System.out.println("End of File has been reached");
		  }
		  
		  catch (FileNotFoundException e1) {
			  
			  System.out.println("File could not be found");
			  
		  } 
		  
		  catch (IOException e) {
			
			  System.out.println("IOException Occured");
			  e.printStackTrace();
		}
		  
	  }
	
		
	// All Exception Cases
	  		
	  
	  /**
		 * This method checks if a 10 digit ISBN is a valid expression by truning each char into a int and adding it all up to check for 11 divisibility.
		 * it appends to a semantic error file if it throws a Bad ISBN13 Exception
		 * 
		 * @param CSValues are the values obtained from the split in seperating the values from a CSV file
		 * @param CSvFile is a String CSV file
		 * @return a boolean expression true if a valid ISBN else false
		 */
	public static boolean Check_ISBN10Exception(String[] CSValues, String CSVFile) {
		
			try 
			{
				int value, sum = 0;
				
				for (int i = 0 ; i < CSValues[3].length() ; i++) 
				{
					char Num = CSValues[3].charAt(i);
					
					if (!Character.isDigit(Num))				// Checking if the ISBN is all numbers
						throw new BadIsbn10Exception();
					
					 value = Character.getNumericValue(Num);
					 
					 sum += value;
				}
		         
				
				if (sum % 11 == 0) {
					return true;
				}
			
				else
					throw new BadIsbn10Exception();
		
			
			} catch (BadIsbn10Exception e) {
				
				
				// Adding To Semantic File
				
				try {
					
					BufferedWriter bw = new BufferedWriter ( new FileWriter ("semantic_error_file.txt",true) );
					
					bw.append("Semantic error in file: " + CSVFile + "\n");
	    			
	    			bw.append("====================\n\n");
	    			
	    			bw.append("Error: Bad ISBN 10 \nRecord: ");
					
					for (int i = 0 ; i < CSValues.length ; i++) {
						
						bw.append(CSValues[i] + ", ");
						
					}
					
					bw.append("\n\n");
					
					bw.close();
					
					return false;
					
				} catch (IOException e1) {
					
					System.out.println("Semantics File cannot be opened");
					
					return false;
					
				}
			}
			
			
		}
		
	  /**
		 * This method checks if a 10 digit ISBN is a valid expression by truning each char into a int and adding it all up to check for 11 divisibility.
		 * It appends to a semantic error file if it throws a Bad ISBN13 Exception
		 * 
		 * @param CSValues are the values obtained from the split in seperating the values from a CSV file
		 * @param CSvFile is a String CSV file
		 * @return a boolean expression true if a valid ISBN else false
		 */
	public static boolean Check_ISBN13Exception(String[] CSValues, String CSVFile) {
			
			try 
			{
				int value, sum = 0;
				
				for (int i = 0 ; i < CSValues[3].length() ; i++) 
				{
					char Num = CSValues[3].charAt(i);
					
					if (!Character.isDigit(Num))				// Checking if the ISBN is all numbers
						throw new BadIsbn13Exception();
					
					 value = Character.getNumericValue(Num);
					 
					 sum += value;
				}
		         
				
				if (sum % 10 == 0) {
					return true;
				}
			
				else
					throw new BadIsbn13Exception();
		
			
			} catch (BadIsbn13Exception e) {
				

				try {
									
					BufferedWriter bw = new BufferedWriter ( new FileWriter ("semantic_error_file.txt",true) );
					
					bw.append("Semantic error in file: " + CSVFile + "\n");
	    			
	    			bw.append("====================\n\n");
	    			
	    			bw.append("Error: Bad ISBN 13 \nRecord: ");
					
					for (int i = 0 ; i < CSValues.length ; i++) {
						
						bw.append(CSValues[i] + ", ");
						
					}
					
					bw.append("\n\n");
					
					bw.close();
					
					return false;
					
				} catch (IOException e1) {
					
					System.out.println("Semantics File cannot be opened");
					
					return false;
					
				}
				
			}
			
			
		}
		
	/**
	 * This method checks if a line has a bad price - meaning a negative number for price  and appends to a semantic error file if it throws Bad Price exception
	 * 
	 * @param CSValues are the values obtained from the split in seperating the values from a CSV file
	 * @param CSvFile is a String CSV file
	 * @return a boolean expression true if a valid ISBN else false
	 */
	public static boolean Check_BadPriceException (String[] CSValues, String CSVFile) {
			
			
			
			try {
				
				double Price = Double.parseDouble(CSValues[2]);
				
				if (Price < 0)
					throw new BadPriceException();
				
				else 
					return true;
			
			}	
			
			// Adding to Semantic File
			
			catch (BadPriceException e) {
				
				try {
									
					BufferedWriter bw = new BufferedWriter ( new FileWriter ("semantic_error_file.txt",true) );
					
					bw.append("Semantic error in file: " + CSVFile + "\n");
	    			
	    			bw.append("====================\n\n");
	    			
	    			bw.append("Error: Bad Price \nRecord: ");
					
					for (int i = 0 ; i < CSValues.length ; i++) {
						
						bw.append(CSValues[i] + ", ");
						
					}
					
					bw.append("\n\n");
					
					bw.close();
					
					return false;
					
				} catch (IOException e1) {
					
					System.out.println("Semantics File cannot be opened");
					
					return false;
					
					
				}
				
				
				
			}
			
			
		}

	/**
	 * This method checks if a line has a bad year (not between 95 and 10) - throws and appends to a semantic error file if bad year
	 * 
	 * @param CSValues is an array obtained from the Line split
	 * @param CSVFile is a String File 
	 * @return boolean true or false
	 */
	public static boolean Check_BadYearException (String[] CSValues, String CSVFile) {
		
		try {
			
			int Year = Integer.parseInt(CSValues[5]);
			
			if (Year < 1995 || Year > 2010) {
				
				throw new BadYearException();
				
			}
			
			else
				return true;
			
			
			// Adding to semantic error file
			
		} catch (BadYearException e) {
			
			try {
								
				BufferedWriter bw = new BufferedWriter ( new FileWriter ("semantic_error_file.txt",true) );
				
				bw.append("Semantic error in file: " + CSVFile + "\n");
    			
    			bw.append("====================\n\n");
    			
    			bw.append("Error: Bad ISBN 13 \nRecord: ");
				
				for (int i = 0 ; i < CSValues.length ; i++) {
					
					bw.append(CSValues[i] + ", ");
					
				}
				
				bw.append("\n\n");
				
				bw.close();
				
				return false;
				
			} catch (IOException e1) {
				
				System.out.println("Semantics File cannot be opened");
				
				return false;
				
			}
			
		}
		
	}
	 	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------------*/		

	// Part 3 Methods

	/**
	 * This method does part 3, it hard codes the size of the array obtained from part 2 - Assign each genre book array to null and then utilizes Binary_to_array
	 * 	to fill the array up. Then it utilizes it's menu to display a selected genre file book.
	 */
    public static void do_part3() {
    	
    	Scanner scanner = new Scanner (System.in);
    	

	    Book[] Cartoons_Comics = new Book[7]; 					// Size obtained from Part 2
		   
	   	Book[] Hobbies_Collectibles = new Book[7]; 			// Size obtained from Part 2
	   	
	   	Book[] 	Movies_TV_Books = new Book[111]; 				// Size obtained from Part 2
	   	
	   	Book[] Music_Radio_Books = new Book[65];				// Size obtained from Part 2	
	   	
	   	Book[] Nostalgia_Eclectic_Books = new Book[4]; 		// Size obtained from Part 2
	   	
	   	Book[] 	Old_Time_Radio_Books = new Book[3]; 			// Size obtained from Part 2
	   	
	   	Book[] 	Sports_Sports_Memorabilia = new Book[27];		// Size obtained from Part 2	
	   		
	   	Book[] Trains_Planes_Automobiles = new Book[2];		// Size obtained from Part 2
    	
	   	
	   	// Storing the Binary file values for each genre into their object array
	   	
	   	Cartoons_Comics = Binary_To_Array("Cartoons_Comics_Books.csv.ser", Cartoons_Comics);
	   	
	   	Hobbies_Collectibles = Binary_To_Array("Hobbies_Collectibles_Books.csv.ser", Hobbies_Collectibles);
	   	
		Movies_TV_Books = Binary_To_Array("Movies_TV.csv.ser", 	Movies_TV_Books);
	   	
		Music_Radio_Books = Binary_To_Array("Music_Radio_Books.csv.ser",  Music_Radio_Books);
	   	
		Nostalgia_Eclectic_Books = Binary_To_Array("Nostalgia_Eclectic_Books.csv.ser", Nostalgia_Eclectic_Books);
	   	
		Old_Time_Radio_Books = Binary_To_Array("Old_Time_Radio.csv.ser", Old_Time_Radio_Books);
	   	
		Sports_Sports_Memorabilia = Binary_To_Array("Sports_Sports_Memorabilia.csv.ser", Sports_Sports_Memorabilia);
	   	
		Trains_Planes_Automobiles = Binary_To_Array("Trains_Planes_Automobiles.csv.ser", Trains_Planes_Automobiles);
		

		String[] Serialized_Files = {
				  "Cartoons_Comics.csv.ser",
	              "Hobbies_Collectibles.csv.ser",
	              "Movies_TV_Books.csv.ser",
	              "Music_Radio_Books.csv.ser",
	              "Nostalgia_Eclectic_Books.csv.ser",
	              "Old_Time_Radio_Books.csv.ser",
	              "Sports_Sports_Memorabilia.csv.ser",
	              "Trains_Planes_Automobiles.csv.ser"
	              };
		
		String Main_Menu_Choice;
    	
    	int Sub_Menu_Choice = 0;
    	
    	Book[] Genre = Cartoons_Comics;
		
		
		do 
		{
			
			Display_Menu(Serialized_Files, Sub_Menu_Choice, Genre);
			
			// Get valid Input from user
			
			do {
				
				Main_Menu_Choice = scanner.next().toLowerCase();
	    	
			} while (!Main_Menu_Choice.equals("v") && !Main_Menu_Choice.equals("s") && !Main_Menu_Choice.equals("x") );
	    	
	    	
			// Option v
			
	    	if (Main_Menu_Choice.equals("v") ) {
	    		
	    		int CurrentIndex = 0;
	    		
	    		System.out.println("Viewing:" + Serialized_Files[Sub_Menu_Choice] + "\t(" + Genre.length + " records)" + "\n");
	    		
	    		while (true) {
	    		
		    		System.out.println("Enter a number (0 to exit): ");
		    		
		    		int n = scanner.nextInt();

		    		if (n == 0) {
		    			System.out.println("Return to Main Menu");
		    			break;
		    		}
		    		
		    		else if (n > 0)
	                {
	                    int i = 0;
	                    
	                    
	                    
	                    try
	                    {
	                    	
	                        for (i = CurrentIndex; i <= (CurrentIndex + (n - 1)) ; i++)
	                            System.out.println(Genre[i]);
	                        
	                    }
	                    
	                    
	                    
	                    catch(ArrayIndexOutOfBoundsException e)
	                    {
	                        System.out.println("EOF has been reached\n");
	                    }


	                    // Update Current Index - happens after catch in the case if EOF gets reached earlier then expected
	                    CurrentIndex = i - 1;

	                }
		    		
		    		 else
		                {
		                    int i;
		                    int BOF_Counter = 0; 
		                   
		                    // index of the first book record displayed on the console, will also be used to set the index of the next current object
		                    int firstOne = (CurrentIndex - (Math.abs(n) - 1));
		                    
		                    for (i = (CurrentIndex - (Math.abs(n) - 1)); i <= CurrentIndex; i++)
		                    {
		                        // If i is negative then we have reached BOF
		                    	
		                        if (i < 0)
		                        {
		                            if (BOF_Counter == 1)
		                                System.out.println("BOF has been reached");
		                            else
		                            {
		                                
		                            }
		                            
		                            BOF_Counter++;
		                        }
		                        else
		                        {
		                            // Display toString of Book[] Genre
		                        	
		                            System.out.println(Genre[i]);
		                        }

		                    }
		                    
		                    
		                    // Checks that if th first book object is negative then the index is still going to be the first
		                    
		                    if (!(firstOne < 0))
		                        CurrentIndex = firstOne;
		                }
	    		
	
	    		
	    		} // end of While Loop
	    		
	    	
	    	} // end of "v" choice if statement
			

	    	
	    	// Option s
	    	
			else if (Main_Menu_Choice.equals("s") ) {
				
				Display_File_Sub_Menu();
		    	
				int option;
				
				// Getting Proper User Input
				
				do {
		    		option = scanner.nextInt();
		    	} while (option > 9 || option < 1);
		    	
				 
				// decrement for the index reasons
				
				if (option == 1) {
					 
					 Sub_Menu_Choice = --option;
					 Genre = Cartoons_Comics;
			            
			        } 
				 
				 
				else if (option == 2) {
			         
					 Sub_Menu_Choice = --option;
					 Genre = Hobbies_Collectibles;
					 
			        } 
				 
				
				else if (option == 3) {

					 Sub_Menu_Choice = --option;  
					 Genre = Movies_TV_Books;
					 
			        } 
				 
				
				else if (option == 4) {
			         
					 Sub_Menu_Choice = --option;
					 Genre = Music_Radio_Books;
					 
			        } 
				 
				
				else if (option == 5) {
					 
					 Sub_Menu_Choice = --option;
					 Genre = Nostalgia_Eclectic_Books;
					 
			        } 
				 
				
				else if (option == 6) {
					 
					 Sub_Menu_Choice = --option;
					 Genre = Old_Time_Radio_Books;
			            
			        } 
				 
				 
				else if (option == 7) {
					 
					 Sub_Menu_Choice = --option;  
					 Genre = Sports_Sports_Memorabilia ;
					 
			        } 
				 
				
				else if (option == 8) {
					 
					 Sub_Menu_Choice =  --option;  
					 Genre = Trains_Planes_Automobiles;
					 
			        } 
				 
				
				else if (Sub_Menu_Choice == 9) {
			            // Nothing happends, just return back to main menu
			        }
		    	
			} // End of option "s" if statement
    	
    	
	    	
			else if (Main_Menu_Choice.equals("x") ) {
				System.out.println("Thank you for using our program - Ending Program...");
				System.exit(0);
			}
				
    
		} while (true);
		
		
		
    }
   
    /**
     * This method simply displays the Main Menu in which the user will interact with
     * 
     * @param Serialized_Files	An array of all the names of the serialized filse from part 2
     * @param index an int value indicating which csvfile will be in use
     * @param Genre	It's used to show the length of the Book array to show how any records there are
     */
    public static void Display_Menu(String[] Serialized_Files,int index, Book[] Genre) {
    	
    	System.out.println("-----------------------------\n"
    			+ "Main Menu\n"
    			+ "-----------------------------\n"
    			+ "v View the selected file: " + Serialized_Files[index] + "\t(" + Genre.length + " records)" + "\n"
    			+ "s Select a file to view\n"
    			+ "x Exit\n"
    			+ "-----------------------------\n"
    			+ "Enter Your Choice: ");

    }
    
    /**
     * This method simply displays the File Sub-Menu, it's displayed when the user selects "s" in the main menu
     */
    public static void Display_File_Sub_Menu() { 
    	
    	int Choice;
    	
    	Scanner scanner = new Scanner (System.in);
    	
    	
    	// Note: We were allowed to hard code the files and it's length
    	
    	System.out.println("------------------------------\n"
    			+ "File Sub-Menu\n"
    			+ "------------------------------\n"
    			+ "1 Cartoons_Comics_Books.csv.ser (7 records)\n"
    			+ "2 Hobbies_Collectibles_Books.csv.ser (7 records)\n"
    			+ "3 Movies_TV.csv.ser (111 records)\n"
    			+ "4 Music_Radio_Books.csv.ser (65 records)\n"
    			+ "5 Nostalgia_Eclectic_Books.csv.ser (4 records)\n"
    			+ "6 Old_Time_Radio.csv.ser (3 records)\n"
    			+ "7 Sports_Sports_Memorabilia.csv.ser (27 records)\n"
    			+ "8 Trains_Planes_Automobiles.csv.ser (2 records)\n"
    			+ "9 Exit\n"
    			+ "------------------------------\n"
    			+ "Enter Your Choice:");

    	
    }
    
    
    /**
     * This file takes in a .csv.ser file produced from part 2 and then stores the objects into the Book Array
     * 
     * @param BinaryFile is a Sring 
     * @param Genre is a Book[]
     * @return a Book[] array with all the proper objects within the proper Genre
     */
    public static Book[] Binary_To_Array (String BinaryFile, Book[] Genre) {
    	
    	try {
    		
    		int i = 0;
    		
    		ObjectInputStream ois = new ObjectInputStream ( new FileInputStream (BinaryFile));
    		
    		while (true) {
    			
    			Genre[i] = (Book) ois.readObject();
    			
    			i++;
    		}
    		
    		
    		
    	}  catch (EOFException e) {
			return Genre;
		}
		
		catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception");
		}
		
		catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception");
		
		} catch (IOException e) {
			
			System.out.println("IOException Occured");
			
		}
    	
    	return Genre;
    }
    
    	

}
