#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <libxml/parser.h>
#include <libxml/tree.h>
#include <libxml/xmlschemastypes.h>
#include <stdbool.h>

//prototypes of the functions
void converter(char *input_file, char *output_file,char *type);
FILE *csvToBinary (char *input_file,char *output_file);
FILE  *binaryToXML (char *input_file,char *output_file);
int validation (char *xml_file,char *xsd_file);
char* substring(char *destination, const char *source, int beg, int n);
FILE *openFile (char *filename,char *type);
int *findCommas(char *str);
uint32_t bigEndVersion (char *str);


typedef struct data {//the struct for the data that read from the csv file
     char name[20];
     char surname[30];
     char gender[5];
     char occupancy[30];
     char level_of_education[4];
     char email[50];
     char bank_account_number[15];
     char IBAN[30];
     char account_type[15];
     char currency_unit[10];
     char total_balance_available[20];
     char available_for_loan[10];
}data;


//"stderr" is a file created inside the <stdio.h> file that writes its contents to both console and terminal. 
// In this project, the error messages are printed to this file with the fprintf function.

int main(int argc,char *argcv[])
{   
    if (argc != 4)  // Number of arguments on command line including program name
    { 
        fprintf(stderr,"Wrong arguments..!");
        fprintf(stderr,"The command line must be in this form : myConverter <input_file> <output_file> <type>\n");
        exit(EXIT_FAILURE); // terminates the program
    }

    // 'char *argcv[]'  is a parameter of type "pointer to pointer". In other words, it shows a sequence of pointers. 
    // Here is this sequence of pointers that shows command-line arguments, including the program name. 
    char *input_file = argcv[1];
    char *output_file = argcv[2];
    char *type = argcv[3];

    converter(input_file,output_file,type); // calls the converter function.
 
    return 0;
 }



void converter(char *input_file, char *output_file,char *type) // the required function is called according to the type argument entered in the terminal
{   
    switch (atoi(type)) // atoi string is a function that returns the numeric characters it encounters in an expression (to int)
    {
        case 1:
            csvToBinary (input_file,output_file);
            break;
        case 2:
            binaryToXML (input_file,output_file);
            break;
        case 3:
            validation (input_file,output_file);
            break;
        default:
            fprintf(stderr,"Invalid type input..!");
            exit(EXIT_FAILURE);
            break;
    }
}


FILE *csvToBinary (char *input_file,char *output_file)
{
    //file pointers that used for reading and writing 
    FILE *fpt, *fp;
    
    char line[500];
 
    fp = openFile(output_file, "wb"); 
    fpt = openFile(input_file,"r");
    //for not to read the first line
    fgets(line,500,fpt);

    while(fgets(line,500,fpt))//if there are still lines to read
    {
        data person; // to store the data 
        char *newline = strtok(line,"\n");
        //to determine the comma indices in the line to tokenize the string
        int *comma_indexes = findCommas(line);
        
        for (int i = 0; i < 12; ++i){//up to 12 because there are 11 attribute 

            //these 3 lines determines the start index and the length of the data
            int start = comma_indexes[i];
            int len = comma_indexes[i+1]- start;
            char word[len];
            //with the help of the substring method takes the charchters inbetween two commas and returns the word
            substring(word, line, start+1, len-1);
            
            //switch case to assing the word into proper attribute
            switch(i){
                case 0:
                    strcpy(person.name, word);
                    break;
                case 1:
                    strcpy(person.surname, word); 
                    break;
                case 2:
                    strcpy(person.gender, word);
                    break;
                case 3:
                    strcpy(person.occupancy, word);
                    break;
                case 4:
                    strcpy(person.level_of_education, word);          
                    break;
                case 5:
                    strcpy(person.email, word);
                    break;
                case 6:
                    strcpy(person.bank_account_number, word);
                    break;
                case 7:
                    strcpy(person.IBAN, word);
                    break;
                case 8:
                    strcpy(person.account_type, word);
                    break;
                case 9:
                    strcpy(person.currency_unit, word);
                    break;
                case 10:
                    strcpy(person.total_balance_available, word);
                    break;
                case 11:
                    strcpy(person.available_for_loan, word);
                    break;
                }
        }    
        fwrite(&person, sizeof(person), 1, fp);//writes the data to file
    }

    if (ferror(fpt)) // if the while loop is exited with an error other than the eof error (if the error is related to the disk or operating system), 
    {                //the "ferror" function will return 1.
        fprintf(stderr, "cannot read file!..\n");
        exit(EXIT_FAILURE);
    }

    fclose(fp);
    fclose(fpt);
}

FILE  *binaryToXML (char *input_file,char *output_file)
{       
    FILE *fp;                       //"wb" is used to write to binary file, "rb" is used to read from binary file.
    fp = openFile (input_file,"rb");// calls openFile function.
    data person;

    xmlDocPtr doc = NULL;
    //needed nodes
    xmlNodePtr root_node = NULL, node = NULL, node1 =NULL, node2 = NULL;
    char buff[256];
    //creating the xml tree
    doc = xmlNewDoc(BAD_CAST "1.0");
    root_node = xmlNewNode(NULL, BAD_CAST "records");//defining root node
    xmlDocSetRootElement(doc, root_node);//assinging root node
    int i = 1;//to track row id



    while(fread(&person, sizeof(data), 1,fp) > 0){
        //creates the row node
        node = xmlNewChild(root_node, NULL, BAD_CAST "row", NULL);
        sprintf(buff, "%d", i);
        xmlNewProp(node, BAD_CAST "id", BAD_CAST buff);//to define the attribute named id and value buff which is the i value

        //creates the node customer info and child nodes  with the read person data
        node1 = xmlNewChild(node, NULL, BAD_CAST "customer_info", NULL);
        xmlNewChild(node1, NULL, BAD_CAST "name", BAD_CAST person.name);
        xmlNewChild(node1, NULL, BAD_CAST "surname", BAD_CAST person.surname);
        xmlNewChild(node1, NULL, BAD_CAST "gender", BAD_CAST person.gender);
        xmlNewChild(node1, NULL, BAD_CAST "occupancy", BAD_CAST person.occupancy);
        xmlNewChild(node1, NULL, BAD_CAST "level_of_education", BAD_CAST person.level_of_education);
        xmlNewChild(node1, NULL, BAD_CAST "email", BAD_CAST person.email);

        //creats the node bank account info and child nodes with the read person data
        node1 = xmlNewChild(node, NULL, BAD_CAST "bank_account_info", NULL);
        xmlNewChild(node1, NULL, BAD_CAST "bank_account_number", BAD_CAST person.bank_account_number);
        xmlNewChild(node1, NULL, BAD_CAST "IBAN", BAD_CAST person.IBAN);
        xmlNewChild(node1, NULL, BAD_CAST "account_type", BAD_CAST person.account_type);
        //creating a new node in order the add attributes
        node2 = xmlNewChild(node1, NULL, BAD_CAST "total_balance_available", person.total_balance_available);
        xmlNewProp(node2, BAD_CAST "currency_unit", BAD_CAST person.currency_unit);
        //computes the big endian version of the total balance available
        uint32_t bigEnd_num = bigEndVersion(person.total_balance_available);
        sprintf(buff,"%lu",bigEnd_num);
        xmlNewProp(node2, BAD_CAST "bigEnd_Version", BAD_CAST buff);
        xmlNewChild(node1, NULL, BAD_CAST "available_for_loan", BAD_CAST person.available_for_loan);
        i++;//to change the row id
    }
    
    if (ferror(fp)) { 
        fprintf(stderr, "cannot read file!..\n");
        exit(EXIT_FAILURE);
    }

    xmlSaveFormatFileEnc(output_file, doc, "UTF-8", 1);//to see the xml tree in an output file
    //xmlSaveFormatFileEnc("-", doc, "UTF-8", 1);  // to display the result in terminal

    fclose(fp);
    xmlFreeDoc(doc);
	xmlCleanupParser();
       
}



uint32_t bigEndVersion (char *str)   //are divided into number bytes and have hexadecimal equivalents. Order of bytes is changed using bitwise operators
{  //"uint32_t" type is used against the errors that may occur due to the integer type that can change from system to system.
    uint32_t num = (uint32_t)atoi(str);
    uint32_t byte0 , byte1, byte2, byte3;
    byte0 = (num & 0x000000FF) >> 0;
    byte1 = (num & 0x0000FF00) >> 8;
    byte2 = (num & 0x00FF0000) >> 16;
    byte3 = (num & 0xFF000000) >> 24;

    return ((byte0 << 24) | (byte1 << 16) | (byte2 << 8) | (byte3 << 0));

}



int validation (char *xml_file,char *xsd_file)
{
    xmlDocPtr doc;
    xmlSchemaPtr schema = NULL;
    xmlSchemaParserCtxtPtr ctxt;
	
    char *XMLFileName = xml_file; // write your xml file here
    char *XSDFileName = xsd_file; // write your xsd file here
    
    
    xmlLineNumbersDefault(1); //set line numbers, 0> no substitution, 1>substitution
    ctxt = xmlSchemaNewParserCtxt(XSDFileName); //create an xml schemas parse context
    schema = xmlSchemaParse(ctxt); //parse a schema definition resource and build an internal XML schema
    xmlSchemaFreeParserCtxt(ctxt); //free the resources associated to the schema parser context
    
    doc = xmlReadFile(XMLFileName, NULL, 0); //parse an XML file
    if (doc == NULL)
    {
        fprintf(stderr, "Could not parse %s\n", XMLFileName);
    }
    else
    {
        xmlSchemaValidCtxtPtr ctxt;  //structure xmlSchemaValidCtxt, not public by API
        int ret;
        
        ctxt = xmlSchemaNewValidCtxt(schema); //create an xml schemas validation context 
        ret = xmlSchemaValidateDoc(ctxt, doc); //validate a document tree in memory
        if (ret == 0) //validated
        {
            printf("%s validates\n", XMLFileName);
        }
        else if (ret > 0) //positive error code number
        {
            printf("%s fails to validate\n", XMLFileName);
        }
        else //internal or API error
        {
            printf("%s validation generated an internal error\n", XMLFileName);
        }
        xmlSchemaFreeValidCtxt(ctxt); //free the resources associated to the schema validation context
        xmlFreeDoc(doc);
    }
    // free the resource
    if(schema != NULL)
        xmlSchemaFree(schema); //deallocate a schema structure

    xmlSchemaCleanupTypes(); //cleanup the default xml schemas types library
    xmlCleanupParser(); //cleans memory allocated by the library itself 
    xmlMemoryDump(); //memory dump
}



FILE *openFile (char *filename,char *type) // This function is called, where checks are also made in file opening operations.
{ 
    FILE *fp;  
    if ((fp = fopen(filename, type)) == NULL) {  // if 'fopen' function returns NULL
        fprintf(stderr, "cannot open file!..\n");
        exit(EXIT_FAILURE);
    }
    return fp;
}




int *findCommas(char *str) //this function stores the indices of the commas in str in an int array
{   
    int *comma_indexes;
    if ((comma_indexes = (int *)malloc(13*sizeof(int))) == NULL) {
        fprintf(stderr, "cannot allocate memory!..\n");
        exit(EXIT_FAILURE);
    }

    comma_indexes[0] = -1;//to specify the start index of the string which is -1 for future operations
    size_t length = strlen(str);
    comma_indexes[12] = length; //to specify the finish index of the string which is the length of the string
    size_t j = 1; // to follow the comma indices 
    for (size_t i = 0; i < length; i++)//this for loop iterates the char array's indices
    {
        if (str[i]==',') //if there is a comma
        {
            comma_indexes[j] = i;//puts it into the comma array
            j++;
        }
    }
    return comma_indexes;//returns the int array
}








// Function to implement substring function in C
char* substring(char *destination, const char *source, int beg, int n)
{
    // extracts `n` characters from the source string starting from `beg` index
    // and copy them into the destination string
    while (n > 0)
    {
        *destination = *(source + beg);
 
        destination++;
        source++;
        n--;
    }
 
    // null terminate destination string
    *destination = '\0';
 
    // return the destination string
    return destination;
}