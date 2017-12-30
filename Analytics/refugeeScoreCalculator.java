import java.util.*;
import java.io.*;

public class refugeeScoreCalculator
{

	public static void main( String args[] ) throws Exception
	{
		if (args.length < 1 )
		{
			System.out.println("\nusage: C:\\> java file for refugees\n\n"); 
			System.exit(0);
		}

		BufferedReader infile = new BufferedReader( new FileReader(args[0]) );
		final int acceptanceCutoff = 600;
		int refugeeAcceptance = 0;
		ArrayList<Integer> refugeeAcceptanceArray = new ArrayList<Integer>();

		while ( infile.ready() )
		{
			String refugeeInfo = infile.readLine();
			String refugeeArray[] = refugeeInfo.split(" ");


			refugeeAcceptance = rankRefugee(refugeeArray, acceptanceCutoff);
			refugeeAcceptanceArray.add(refugeeAcceptance);

			System.out.println(refugeeAcceptance);
		}

	}

	static int rankRefugee(String[] refugeeArray, int acceptanceCutoff)
    {
    	int refugeeID = Integer.parseInt(refugeeArray[0]);
		int refugeeAge = Integer.parseInt(refugeeArray[1]);
		String refugeeSex = refugeeArray[2];
		String refugeeSpouse = refugeeArray[3];
		String refugeeEducation = refugeeArray[4];
		int refugeeCLBReading = Integer.parseInt(refugeeArray[5]);
		int refugeeCLBWriting = Integer.parseInt(refugeeArray[6]);
		int refugeeCLBSpeaking = Integer.parseInt(refugeeArray[7]);
		int refugeeCLBListening = Integer.parseInt(refugeeArray[8]);
		int refugeeCanadianWorkExperience = Integer.parseInt(refugeeArray[9]);
		String refugeeSpouseEducation = "Incomplete_High_School";
		int refugeeSpouseCLBReading = 0;
		int refugeeSpouseCLBWriting = 0;
		int refugeeSpouseCLBSpeaking = 0;
		int refugeeSpouseCLBListening = 0;
		int refugeeSpouseCanadianWorkExperience = 0;
		if (refugeeSpouse.contains("YES")) {
			refugeeSpouseEducation = refugeeArray[10];
			refugeeSpouseCLBReading = Integer.parseInt(refugeeArray[11]);
			refugeeSpouseCLBWriting = Integer.parseInt(refugeeArray[12]);
			refugeeSpouseCLBSpeaking = Integer.parseInt(refugeeArray[13]);
			refugeeSpouseCLBListening = Integer.parseInt(refugeeArray[14]);
			refugeeSpouseCanadianWorkExperience = Integer.parseInt(refugeeArray[15]);
		}
		int refugeeForeignWorkExperience = Integer.parseInt(refugeeArray[16]);
		int refugeeCanadianSiblings = Integer.parseInt(refugeeArray[17]);
		int refugeeNCLCReading = Integer.parseInt(refugeeArray[18]);
		int refugeeNCLCWriting = Integer.parseInt(refugeeArray[19]);
		int refugeeNCLCSpeaking = Integer.parseInt(refugeeArray[20]);
		int refugeeNCLCListening = Integer.parseInt(refugeeArray[21]);
		String refugeePostSecondaryCanada = refugeeArray[22];
		String refugeeArrangedEmployment = refugeeArray[23];
		String refugeePNNomination = refugeeArray[24];
		int refugeeTradeCertificate = Integer.parseInt(refugeeArray[25]);

		int A = rankA(refugeeAge, refugeeEducation, 
			refugeeCLBListening, refugeeCLBSpeaking, refugeeCLBWriting, refugeeCLBReading, 
			refugeeNCLCListening, refugeeNCLCSpeaking, refugeeNCLCWriting, refugeeNCLCReading, 
			refugeeCanadianWorkExperience, refugeeSpouse);

		int B = rankB(refugeeSpouse, refugeeSpouseEducation, refugeeSpouseCLBReading, refugeeSpouseCLBWriting, refugeeSpouseCLBListening, refugeeSpouseCLBSpeaking, refugeeSpouseCanadianWorkExperience);

		int C = rankC(refugeeEducation, refugeeCLBReading, refugeeCLBWriting, refugeeCLBListening, refugeeCLBSpeaking, refugeeCanadianWorkExperience, refugeeForeignWorkExperience, refugeeTradeCertificate);
		
		int Total = A + B + C;
		if (Total > 600) {
			Total = 600;
		}
		int D = rankD(refugeeCanadianSiblings, refugeeNCLCWriting, refugeeNCLCListening, refugeeNCLCSpeaking, refugeeNCLCReading,
		refugeeCLBReading, refugeeCLBWriting, refugeeCLBListening, refugeeCLBSpeaking, refugeePostSecondaryCanada, refugeeArrangedEmployment, refugeePNNomination);

		Total = Total + D;
		//System.out.println(Total);
		if (Total > acceptanceCutoff) {
			return 1;
		} else {
			return 0;
		}
	}

	static int rankA(int refugeeAge, String refugeeEducation, 
		int refugeeCLBListening, int refugeeCLBSpeaking, int refugeeCLBWriting, int refugeeCLBReading,
		int refugeeNCLCListening, int refugeeNCLCSpeaking, int refugeeNCLCWriting, int refugeeNCLCReading,
		int refugeeCanadianWorkExperience, String refugeeSpouse)
	{

		int refugeeAgeScore = refugeeAgeCheck(refugeeAge, refugeeSpouse);
		int refugeeEducationScore = refugeeEducationCheck(refugeeEducation, refugeeSpouse, 0);
		int refugeeEnglishLanguageScore = refugeeEnglishLanguageCheck(refugeeCLBListening, refugeeCLBWriting, refugeeCLBSpeaking, refugeeCLBReading, refugeeSpouse);
		int refugeeFrenchLanguageScore = refugeeFrenchOrSpouseLanguageCheck(refugeeNCLCListening, refugeeNCLCWriting, refugeeNCLCSpeaking, refugeeNCLCReading, refugeeSpouse, 0);
		int refugeeWorkExperienceScore = refugeeWorkExperienceCheck(refugeeCanadianWorkExperience, refugeeSpouse, 0);

		int rankAScore = refugeeAgeScore + refugeeEducationScore + refugeeEnglishLanguageScore + refugeeFrenchLanguageScore + refugeeWorkExperienceScore;

		if (refugeeSpouse.contains("NO")) {
			if (rankAScore > 500) {
				rankAScore = 500;
			}
		} else {
			if (rankAScore > 460) {
				rankAScore = 460;
			}
		}
		return rankAScore;
	}

	static int rankB(String refugeeSpouse, String refugeeSpouseEducation, int refugeeSpouseCLBReading, int refugeeSpouseCLBWriting, int refugeeSpouseCLBListening, int refugeeSpouseCLBSpeaking, int refugeeSpouseWorkExperience)
	{
		int spouseEducationScore = 0;
		if (refugeeSpouse.contains("YES")) {
			spouseEducationScore = refugeeEducationCheck(refugeeSpouseEducation, refugeeSpouse, 1);
		}

		int spouseLanguageScore = 0;
		if (refugeeSpouse.contains("YES")) {
			spouseLanguageScore = refugeeFrenchOrSpouseLanguageCheck(refugeeSpouseCLBListening, refugeeSpouseCLBWriting, refugeeSpouseCLBSpeaking, refugeeSpouseCLBReading, refugeeSpouse, 1);
		}

		int spouseWorkScore = 0;
		if (refugeeSpouse.contains("YES")) {
			spouseWorkScore = refugeeWorkExperienceCheck(refugeeSpouseWorkExperience, refugeeSpouse, 1);
		}

		int rankBScore = spouseEducationScore + spouseLanguageScore + spouseWorkScore;
		return rankBScore;
	}

	static int rankC(String refugeeEducation, int refugeeCLBReading, int refugeeCLBWriting, int refugeeCLBListening, int refugeeCLBSpeaking, int refugeeCanadianWorkExperience, int refugeeForeignWorkExperience, int refugeeTradeCertificate)
	{
		//education
		int educationScore = 0;
		if (refugeeEducation.contains("Bachelors") || refugeeEducation.contains("1_Year_Degree") || refugeeEducation.contains("2_Year_Degree") || refugeeEducation.contains("3_Year_Degree")) {
			if (refugeeCLBReading >= 7 && refugeeCLBWriting >= 7 && refugeeCLBListening >= 7 && refugeeCLBSpeaking >= 7) {
				if (refugeeCLBReading >= 9 && refugeeCLBWriting >= 9 && refugeeCLBListening >= 9 && refugeeCLBSpeaking >= 9) {
					educationScore = 25;
				} else {
					educationScore = 13;
				}
			}
		} else if (refugeeEducation.contains("Masters") || refugeeEducation.contains("2_Degrees") || refugeeEducation.contains("PhD")) {
			if (refugeeCLBReading >= 7 && refugeeCLBWriting >= 7 && refugeeCLBListening >= 7 && refugeeCLBSpeaking >= 7) {
				if (refugeeCLBReading >= 9 && refugeeCLBWriting >= 9 && refugeeCLBListening >= 9 && refugeeCLBSpeaking >= 9) {
					educationScore = 50;
				} else {
					educationScore = 25;
				}
			}
		}

		if (refugeeEducation.contains("Bachelors") || refugeeEducation.contains("1_Year_Degree") || refugeeEducation.contains("2_Year_Degree") || refugeeEducation.contains("3_Year_Degree")) {
			if (refugeeCanadianWorkExperience > 0) {
				if (refugeeCanadianWorkExperience > 1) {
					educationScore = educationScore + 25;
				} else {
					educationScore = educationScore + 13;
				}
			}
		} else if (refugeeEducation.contains("Masters") || refugeeEducation.contains("2_Degrees") || refugeeEducation.contains("PhD")) {
			if (refugeeCanadianWorkExperience > 0) {
				if (refugeeCanadianWorkExperience > 1) {
					educationScore = educationScore + 50;
				} else {
					educationScore = educationScore + 25;
				}
			}
		}

		if (refugeeTradeCertificate > 0) {
			if (refugeeCLBReading >= 5 && refugeeCLBWriting >= 5 && refugeeCLBListening >= 5 && refugeeCLBSpeaking >= 5) {
				if (refugeeCLBReading >= 7 && refugeeCLBWriting >= 7 && refugeeCLBListening >= 7 && refugeeCLBSpeaking >= 7) {
					educationScore = educationScore + 50;
				} else {
					educationScore = educationScore + 25;
				}
			}
		}

		//Foreign Work
		int  foreignWorkScore = 0;
		if (refugeeForeignWorkExperience == 1 || refugeeForeignWorkExperience == 2) {
			if (refugeeCLBReading >= 7 && refugeeCLBWriting >= 7 && refugeeCLBListening >= 7 && refugeeCLBSpeaking >= 7) {
				if (refugeeCLBReading >= 9 && refugeeCLBWriting >= 9 && refugeeCLBListening >= 9 && refugeeCLBSpeaking >= 9) {
					foreignWorkScore = 25;
				} else {
					foreignWorkScore = 13;
				}
			}
		} else if (refugeeForeignWorkExperience >= 3) {
			if (refugeeCLBReading >= 7 && refugeeCLBWriting >= 7 && refugeeCLBListening >= 7 && refugeeCLBSpeaking >= 7) {
				if (refugeeCLBReading >= 9 && refugeeCLBWriting >= 9 && refugeeCLBListening >= 9 && refugeeCLBSpeaking >= 9) {
					foreignWorkScore = 50;
				} else {
					foreignWorkScore = 25;
				}
			}
		}

		if (refugeeForeignWorkExperience == 1 || refugeeForeignWorkExperience == 2) {
			if (refugeeCanadianWorkExperience > 0) {
				if (refugeeCanadianWorkExperience > 1) {
					educationScore = educationScore + 25;
				} else {
					educationScore = educationScore + 13;
				}
			}
		} else if (refugeeForeignWorkExperience >= 3) {
			if (refugeeCanadianWorkExperience > 0) {
				if (refugeeCanadianWorkExperience > 1) {
					educationScore = educationScore + 50;
				} else {
					educationScore = educationScore + 25;
				}
			}
		}

		int rankCScore = educationScore + foreignWorkScore;
		return rankCScore;
	}

	static int rankD(int refugeeCanadianSiblings, int refugeeNCLCWriting, int refugeeNCLCListening, int refugeeNCLCSpeaking, int refugeeNCLCReading,
		int refugeeCLBReading, int refugeeCLBWriting, int refugeeCLBListening, int refugeeCLBSpeaking, String refugeePostSecondaryCanada, String refugeeArrangedEmployment, String refugeePNNomination) {

		int refugeeSiblingScore = 0;
		if (refugeeCanadianSiblings > 0) {
			refugeeSiblingScore = 15;
		}

		int frenchEducationScore = 0;
		if (refugeeNCLCReading >= 7 && refugeeNCLCWriting >= 7 && refugeeNCLCSpeaking >= 7 && refugeeNCLCListening >= 7) {
			if (refugeeCLBReading >= 5 && refugeeCLBWriting >= 5 && refugeeCLBListening >= 5 && refugeeCLBSpeaking >= 5) {
				
				frenchEducationScore = 30;
			} else {
				frenchEducationScore = 15;
			}
		} 

		int canadianEducationScore = 0;
		if (refugeePostSecondaryCanada.contains("1_Year_Degree") || refugeePostSecondaryCanada.contains("2_Year_Degree") || refugeePostSecondaryCanada.contains("3_Year_Degree")) {
			canadianEducationScore = 15;
		} else if (refugeePostSecondaryCanada.contains("3_Year_Degree") || refugeePostSecondaryCanada.contains("Bachelors") || refugeePostSecondaryCanada.contains("Masters") || refugeePostSecondaryCanada.contains("PhD")) {
			canadianEducationScore = 30;
		}

		int arrangedEmploymentScore = 0;
		if (refugeeArrangedEmployment.contains("NOC_00")) {
			arrangedEmploymentScore = 200;
		} else if (refugeeArrangedEmployment.contains("Noc_0") || refugeeArrangedEmployment.contains("Noc_A") || refugeeArrangedEmployment.contains("Noc_B")) {
			arrangedEmploymentScore = 50;
		}

		int PNScore = 0;
		if (refugeePNNomination.contains("YES")) {
			PNScore = 600;
		}

		int rankDScore = refugeeSiblingScore + frenchEducationScore + canadianEducationScore + arrangedEmploymentScore + PNScore;
		if (rankDScore > 600) {
			rankDScore = 600;
		}
		return rankDScore;
	}

	static int refugeeAgeCheck(int refugeeAge, String refugeeSpouse) 
	{
		int value = 0;

		if (refugeeAge <= 17 || refugeeAge >= 45) {
			value = 0;
		} else if (refugeeAge == 18 || refugeeAge == 31){
			value = 90;
		} else if (refugeeAge == 19 || refugeeAge == 30) {
			value = 95;
		} else if (20 < refugeeAge && refugeeAge < 29){
			value = 100;
		} else if (refugeeAge == 32){
			value = 85;
		} else if (refugeeAge == 33){
			value = 80;
		} else if (refugeeAge == 34){
			value = 75;
		} else if (refugeeAge == 35){
			value = 70;
		} else if (refugeeAge == 36){
			value = 65;
		} else if (refugeeAge == 37){
			value = 60;
		} else if (refugeeAge == 38){
			value = 55;
		} else if (refugeeAge == 39){
			value = 50;
		} else if (refugeeAge == 40){
			value = 45;
		} else if (refugeeAge == 41){
			value = 35;
		} else if (refugeeAge == 42){
			value = 25;
		} else if (refugeeAge == 43){
			value = 15;
		} else if (refugeeAge == 44){
			value = 5;
		}

		if (refugeeSpouse == "YES") {
			return value;

		} else {
			value = value + ((int) Math.ceil(value / 10.0));
			return value;
		}
		
	}

	static int refugeeEducationCheck(String refugeeEducation, String refugeeSpouse, int spouseVar) 
	{
		int value = 0;

		if (refugeeEducation == "Incomplete_High_School") {
			value = 0;
		} else if (refugeeEducation.contains("High_School")) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 30;
				} else {
					value = 28;
				}
			} else {
				value = 2;
			}
		} else if (refugeeEducation.contains("1_Year_Degree")) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 90;
				} else {
					value = 84;
				}
			} else {
				value = 6;
			}
		} else if (refugeeEducation.contains("2_Year_Degree")) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 98;
				} else {
					value = 90;
				}
			} else {
				value = 7;
			}
		} else if (refugeeEducation.contains("Bachelors")){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 120;
				} else {
					value = 112;
				} 
			} else {
				value = 8;
			}
		} else if (refugeeEducation.contains("3_Year_Degree")){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 120;
				} else {
					value = 112;
				} 
			} else {
				value = 8;
			}
		} else if (refugeeEducation.contains("2_Degrees")){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 128;
				} else {
					value = 119;
				} 
			} else {
				value = 9;
			}
		} else if (refugeeEducation.contains("Masters")){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 135;
				} else {
					value = 126;
				} 
			} else {
				value = 10;
			}
		} else if (refugeeEducation.contains("PhD")){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 150;
				} else {
					value = 140;
				} 
			} else {
				value = 10;
			}
		}

		return value;
			
	}

	static int refugeeEnglishLanguageCheck(int refugeeCLBListening, int refugeeCLBWriting, int refugeeCLBSpeaking, int refugeeCLBReading, String refugeeSpouse) 
	{
		int listeningValue = 0;
		int writingValue = 0;
		int speakingValue = 0;
		int readingValue = 0;

		if (refugeeCLBListening < 4) {
			listeningValue = 0;
		} else if (refugeeCLBListening == 4 || refugeeCLBListening == 5) {
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 6;
			} else {
				listeningValue = 6;
			}
		} else if (refugeeCLBListening == 6) {
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 9;
			} else {
				listeningValue = 8;
			}
		} else if (refugeeCLBListening == 7) {
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 17;
			} else {
				listeningValue = 16;
			}
		} else if (refugeeCLBListening == 8){
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 23;
			} else {
				listeningValue = 22;
			}
		} else if (refugeeCLBListening == 9){
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 31;
			} else {
				listeningValue = 29;
			}
		} else if (refugeeCLBListening >= 10){
			if (refugeeSpouse.contains("NO")) {
				listeningValue = 34;
			} else {
				listeningValue = 32;
			}
		}

		if (refugeeCLBWriting < 4) {
			writingValue = 0;
		} else if (refugeeCLBWriting == 4 || refugeeCLBWriting == 5) {
			if (refugeeSpouse.contains("NO")) {
				writingValue = 6;
			} else {
				writingValue = 6;
			}
		} else if (refugeeCLBWriting == 6) {
			if (refugeeSpouse.contains("NO")) {
				writingValue = 9;
			} else {
				writingValue = 8;
			}
		} else if (refugeeCLBWriting == 7) {
			if (refugeeSpouse.contains("NO")) {
				writingValue = 17;
			} else {
				writingValue = 16;
			}
		} else if (refugeeCLBWriting == 8){
			if (refugeeSpouse.contains("NO")) {
				writingValue = 23;
			} else {
				writingValue = 22;
			}
		} else if (refugeeCLBWriting == 9){
			if (refugeeSpouse.contains("NO")) {
				writingValue = 31;
			} else {
				writingValue = 29;
			}
		} else if (refugeeCLBWriting >= 10){
			if (refugeeSpouse.contains("NO")) {
				writingValue = 34;
			} else {
				writingValue = 32;
			}
		}

		if (refugeeCLBSpeaking < 4) {
			speakingValue = 0;
		} else if (refugeeCLBSpeaking == 4 || refugeeCLBSpeaking == 5) {
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 6;
			} else {
				speakingValue = 6;
			}
		} else if (refugeeCLBSpeaking == 6) {
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 9;
			} else {
				speakingValue = 8;
			}
		} else if (refugeeCLBSpeaking == 7) {
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 17;
			} else {
				speakingValue = 16;
			}
		} else if (refugeeCLBSpeaking == 8){
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 23;
			} else {
				speakingValue = 22;
			}
		} else if (refugeeCLBSpeaking == 9){
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 31;
			} else {
				speakingValue = 29;
			}
		} else if (refugeeCLBSpeaking >= 10){
			if (refugeeSpouse.contains("NO")) {
				speakingValue = 34;
			} else {
				speakingValue = 32;
			}
		}

		if (refugeeCLBReading < 4) {
			readingValue = 0;
		} else if (refugeeCLBReading == 4 || refugeeCLBReading == 5) {
			if (refugeeSpouse.contains("NO")) {
				readingValue = 6;
			} else {
				readingValue = 6;
			}
		} else if (refugeeCLBReading == 6) {
			if (refugeeSpouse.contains("NO")) {
				readingValue = 9;
			} else {
				readingValue = 8;
			}
		} else if (refugeeCLBReading == 7) {
			if (refugeeSpouse.contains("NO")) {
				readingValue = 17;
			} else {
				readingValue = 16;
			}
		} else if (refugeeCLBReading == 8){
			if (refugeeSpouse.contains("NO")) {
				readingValue = 23;
			} else {
				readingValue = 22;
			}
		} else if (refugeeCLBReading == 9){
			if (refugeeSpouse.contains("NO")) {
				readingValue = 31;
			} else {
				readingValue = 29;
			}
		} else if (refugeeCLBReading >= 10){
			if (refugeeSpouse.contains("NO")) {
				readingValue = 34;
			} else {
				readingValue = 32;
			}
		}

		int value = readingValue + speakingValue + listeningValue + writingValue;
		return value;
			
	}

	static int refugeeFrenchOrSpouseLanguageCheck(int refugeeNCLCListening, int refugeeNCLCWriting, int refugeeNCLCSpeaking, int refugeeNCLCReading, String refugeeSpouse, int spouseVar) 
	{
		int listeningValue = 0;
		int writingValue = 0;
		int speakingValue = 0;
		int readingValue = 0;

		if (refugeeNCLCListening <= 4) {
			listeningValue = 0;
		} else if (refugeeNCLCListening == 6 || refugeeNCLCListening == 5) {
			
			listeningValue = 1;		
		} else if (refugeeNCLCListening == 7 || refugeeNCLCListening == 8) {
			
			listeningValue = 3;	
		} else if (refugeeNCLCListening >= 9) {
			if (spouseVar == 1) {
				listeningValue = 5;
			} else {
				listeningValue = 6;
			}
		}

		if (refugeeNCLCWriting <= 4) {
			writingValue = 0;
		} else if (refugeeNCLCWriting == 6 || refugeeNCLCWriting == 5) {
			
			writingValue = 1;		
		} else if (refugeeNCLCWriting == 7 || refugeeNCLCWriting == 8) {
			
			writingValue = 3;	
		} else if (refugeeNCLCWriting >= 9) {
			if (spouseVar == 1) {
				listeningValue = 5;
			} else {
				listeningValue = 6;
			}
		}

		if (refugeeNCLCSpeaking <= 4) {
			speakingValue = 0;
		} else if (refugeeNCLCSpeaking == 6 || refugeeNCLCSpeaking == 5) {
			
			speakingValue = 1;		
		} else if (refugeeNCLCSpeaking == 7 || refugeeNCLCSpeaking == 8) {
			
			speakingValue = 3;	
		} else if (refugeeNCLCSpeaking >= 9) {
			if (spouseVar == 1) {
				listeningValue = 5;
			} else {
				listeningValue = 6;
			}
		}

		if (refugeeNCLCReading <= 4) {
			readingValue = 0;
		} else if (refugeeNCLCReading == 6 || refugeeNCLCReading == 5) {
			
			readingValue = 1;		
		} else if (refugeeNCLCReading == 7 || refugeeNCLCReading == 8) {
			
			readingValue = 3;	
		} else if (refugeeNCLCReading >= 9) {
			if (spouseVar == 1) {
				listeningValue = 5;
			} else {
				listeningValue = 6;
			}
		}

		int value = readingValue + speakingValue + listeningValue + writingValue;

		if (refugeeSpouse.contains("NO")) {
			return value;
		} else {
			if (value > 22) {
				return 22;
			} else {
				return value;
			}
		}	
	}

	static int refugeeWorkExperienceCheck(int refugeeCanadianWorkExperience, String refugeeSpouse, int spouseVar) 
	{

		int value = 0;

		if (refugeeCanadianWorkExperience < 1) {
			value = 0;
		} else if (refugeeCanadianWorkExperience == 1) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 40;
				} else {
					value = 35;
				}
			} else {
				value = 5;
			}
		} else if (refugeeCanadianWorkExperience == 2) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 53;
				} else {
					value = 46;
				}
			} else {
				value = 7;
			}
		} else if (refugeeCanadianWorkExperience == 3) {
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 64;
				} else {
					value = 56;
				}
			} else {
				value = 8;
			}
		} else if (refugeeCanadianWorkExperience == 4){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 72;
				} else {
					value = 63;
				}
			} else {
				value = 9;
			}
		} else if (refugeeCanadianWorkExperience >= 5){
			if (spouseVar == 0) {
				if (refugeeSpouse.contains("NO")) {
					value = 80;
				} else {
					value = 70;
				}
			} else {
				value = 10;
			}
		} 

		return value;
	}

}