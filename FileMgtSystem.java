import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FileMgtSystem {
    public static void main(String[] args) {

        String baseaddr="/home/cen/Desktop/";
        String downloadplace="/home/cen/Downloads";

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("\n#what do you want to do?\n");
            System.out.print("" +
                    "[ 1 ] Create a new Course folder." +
                    "\n[ 2 ] Copy learning material from download folder." +
                    "\n[ 3 ] Display what learning materials in the course folder. " +
                    "\n[ 4 ] Move learning material to another course folder."+
                    "\n[ 5 ] Delete a course folder or a learning material."+
                    "\n[ 0 ] Quit.");
            System.out.print("\n-->Press a key followed by ENTER: ");

            String choice = input.next();

            switch (choice) {
                case "1":
                    System.out.println("\n--You want to create a new course folder.");
                    System.out.print("-->Enter Course name:  ");
                    Scanner newc = new Scanner(System.in);
                    String newcourse = newc.nextLine();
                    createNewfile(baseaddr, newcourse);

                    break;

                case "2":
                    String downloadnewestfile=getnewestfilename(downloadplace);
                    System.out.println("\n...You will copy ["+downloadnewestfile+"] from download folder.");

                    System.out.println("...Which course folder do you want to add the learning material to?");

                    String disp[];
                    disp=getfolderfiles(baseaddr);

                    int t=0;
                    while(disp[t]!=null){
                        System.out.println("[ "+t +" ]"+ " " + disp[t]);
                        t=t+1;
                    }
                    System.out.print("-->Please enter the NUMBER: ");

                    Scanner userchoicefile = new Scanner(System.in);
                    int filechoose = userchoicefile.nextInt();
                    System.out.println("["+downloadnewestfile+"] already copy to "+ disp[filechoose]);

                    String sourcefile=downloadplace+"/"+downloadnewestfile;
                    String destinationfile=baseaddr+disp[filechoose];

                    copyFile(sourcefile,destinationfile);

                    break;

                case "3":
                    System.out.println("\n...Which course or folder do you want to query the file list in?");

                    displayfiles(baseaddr);

                    String[] dispdetail=getfolderfiles(baseaddr);
                    System.out.print("-->Please enter the NUMBER: ");
                    Scanner newd = new Scanner(System.in);
                    String folderdetail = newd.nextLine();
                    System.out.println("--Folder ["+dispdetail[Integer.valueOf(folderdetail).intValue()]+"] have following learning materials: ");

                    String detailaddr2=baseaddr+dispdetail[Integer.valueOf(folderdetail).intValue()];

                    displayfiles(detailaddr2);

                    break;


                case "4":
                    System.out.println("\n...Which course  folder or file do you want to Move(Rename)?");
                    System.out.println("\n...First Step, enter a course folder.");
                    displayfiles(baseaddr);
                    String[] disdetail=getfolderfiles(baseaddr);

                    System.out.print("-->Please enter the NUMBER: ");
                    Scanner selectfolder = new Scanner(System.in);
                    String sfolder = selectfolder.nextLine();
                    System.out.println("\n...Course folder ["+disdetail[Integer.valueOf(sfolder).intValue()]+"] have following learning materials: ");
                    String folderaddr=baseaddr+disdetail[Integer.valueOf(sfolder).intValue()];

                    displayfiles(folderaddr);

                    System.out.print("-->Please select a learning material you want to move, enter the NUMBER:  ");
                    Scanner scourcefilescanner = new Scanner(System.in);
                    String scourcefilenumber= scourcefilescanner.nextLine();

                    String[] sourcedetai=getfolderfiles(folderaddr);

                    String scourcefile=folderaddr+"/"+sourcedetai[Integer.valueOf(scourcefilenumber).intValue()];

/////////////////////////////////////////////////////////////////////////////////////////////////////
                    System.out.println("\n...Second Step, select the target course folder.");
                    displayfiles(baseaddr);
                    System.out.print("-->Please enter the NUMBER: ");

                    Scanner selecttarget = new Scanner(System.in);
                    String starget = selectfolder.nextLine();
                    System.out.println("...Course folder ["+disdetail[Integer.valueOf(starget).intValue()]+"] has been selected.");
                    String targetaddr=baseaddr+disdetail[Integer.valueOf(starget).intValue()];


                    try{
                        String[] cpcommand={"mv","-v",scourcefile,targetaddr};
                        Process process = Runtime.getRuntime().exec(cpcommand);

                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                        reader.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;

                case "5":

                    System.out.println("\n...Which course folder or file do you want to Delete?");
                    System.out.println("\n...Select a course folder.");
                    displayfiles(baseaddr);
                    String[] removefoderdetail=getfolderfiles(baseaddr);

                    Scanner selectedfolder = new Scanner(System.in);
                    System.out.print("-->Please enter the NUMBER: ");
                    String rmfoderdetail = selectedfolder.nextLine();
                    System.out.print("\n...You have selected "+removefoderdetail[Integer.valueOf(rmfoderdetail).intValue()]+", what do you want to do?");


                    System.out.print("\n[ 1 ] Delete the folder. \n[ 2 ] Enter the folder.\n[ other input ] Return to  Main Menu. \n-->Please enter the NUMBER:");
                    Scanner deletfolder = new Scanner(System.in);
                    String defolder = deletfolder.next();
                    if(defolder.equals("1")){
                        try{
                            String removefolderaddr=baseaddr+removefoderdetail[Integer.valueOf(rmfoderdetail).intValue()];
                            System.out.print("removefolderaddr: "+removefolderaddr);
                            String[] rmcommand={"rm","-rvf",removefolderaddr};
                            Process process = Runtime.getRuntime().exec(rmcommand);

                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                            reader.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (defolder.equals("2")) {


                        System.out.println("\n...Course folder [" + removefoderdetail[Integer.valueOf(rmfoderdetail).intValue()] + "] have following learning materials: ");
                        String dfolderaddr = baseaddr + removefoderdetail[Integer.valueOf(rmfoderdetail).intValue()];
                        displayfiles(dfolderaddr);
                        System.out.print("-->Which learning material do you want to delete: ");

                        Scanner deletefile = new Scanner(System.in);
                        String dlfile = deletefile.nextLine();
                        String[] deletedetai = getfolderfiles(dfolderaddr);
                        String deleteaddr = dfolderaddr + "/" + deletedetai[Integer.valueOf(dlfile).intValue()];

                        try {
                            String[] rmcommand = {"rm", "-rvf", deleteaddr};
                            Process process = Runtime.getRuntime().exec(rmcommand);

                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        break;
                    break;

                case "0":
                    System.out.println("--The End--");
                    return;

            }

        }


    }


    public static void createNewfile(String baseaddr, String courseName){
        try {
            String mergefileaddrandname=baseaddr+courseName;

            String[] newclassaddr={"mkdir",mergefileaddrandname};

            Process process = Runtime.getRuntime().exec(newclassaddr);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void copyFile(String s, String d){

        try{
            String[] cpcommand={"cp",s,d};
            Process process = Runtime.getRuntime().exec(cpcommand);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    //input folder address, return the newest file name in the folder
    public static String getnewestfilename(String orgaddr) {
        String newestfile=null; // Initialize `newestFile` to avoid uninitialized return
        try {
            String execstring = "ls -t " + orgaddr;
            Process process = Runtime.getRuntime().exec(execstring);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            newestfile = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newestfile;
    }

    //input folder address, return filenames stored in a string list.
    public static String[] getfolderfiles(String orgaddr){
        String[] filelist = new String[30];
        int i = 0;
        try {
            String[] comdexc={"ls","-t",orgaddr};
            Process process = Runtime.getRuntime().exec(comdexc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                filelist[i] = line;
                i = i + 1;
            }reader.close();
        } catch (IOException e) {e.printStackTrace();}
        return filelist;

    }

    public static void displayfiles(String addr){
        String folderfiles[];
        folderfiles=getfolderfiles(addr);
        int i=0;
        while(folderfiles[i]!=null){
            System.out.println("[ "+i+" ]"+ " " + folderfiles[i]);
            i=i+1;
        }

    }
}