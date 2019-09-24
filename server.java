package prog1;
import java.net.*;
import java.io.*;

public class server {

	public static void main(String[] args) throws IOException {


		//Creating the socket and port for the client to connect to

		ServerSocket ss = new ServerSocket(2222);
		Socket s = ss.accept();
		InetAddress clientIP = s.getInetAddress();

		System.out.println("Client Connected");
		System.out.println("Client IP : " + clientIP.toString());

		//Used to send output to the client
		PrintWriter output = new PrintWriter(s.getOutputStream());

		//Random Number generator 
		int ranNum = (int)(Math.random() * 100);
		int cNum = -1;

		boolean done  = false;

		System.out.println("The number is " + ranNum);

		output.println("+ Hello! I am thinking of a number between 0 and 100.");
		output.flush();

		//Used to get input from the client
		InputStreamReader input = new InputStreamReader(s.getInputStream());
		BufferedReader buff = new BufferedReader(input);


		//Loops over and over listening for input from the client and
		//sending responses
		while(!done) {

			//Gets a line of input from the input stream
			String cGuess = buff.readLine();

			//Checks for a null value on the input stream
			//if null then  client disconnected close socket.
			if (cGuess == null) {
				s.close();
				done = true;
			}

			System.out.println("client : " + cGuess);

			//Repeating the clients guess to the client
			output.println("You guessed " + cGuess);
			output.flush();


			//A try catch block to parse the clients guess into an int value
			//Catchs a number format exception and prompts client for
			//proper guess. Other wise parses to an int value and 
			//tests guess and responds to the client with too high,
			//too low, number out of range, or correct.
			try {
				cNum = Integer.parseInt(cGuess);

				if (cNum > 100 || cNum < 0) {
					output.println("! Please guess a number between 0 and 100.");
					output.flush();
				}
				else if (ranNum > cNum) {
					output.println("< Too Low!");
					output.flush();
				}
				else if (ranNum < cNum) {

					output.println("> Too High!");
					output.flush();
				}
				else {
					output.println("* Correct Well Done! Goodbye.");
					output.flush();
					done = true;
				}
			}
			catch (NumberFormatException e)
			{
				output.println("! Invalid input. Please Type a Number");
				output.flush();
			}
		}

		//We are done here. Close the socket.
		s.close();
	}

}
