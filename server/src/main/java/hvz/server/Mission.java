package hvz.server;

public class Mission{

	public String gameCode;
    public String humanObj;
	public String zombieObj;
    public String title;
	public int isCompleted;
	
        public Mission(String gameCode, String title, String zombieObj, String humanObj, int isCompleted){
			this.isCompleted = isCompleted;  
			this.gameCode = gameCode;
			this.title = title;
			this.zombieObj = zombieObj;
			this.humanObj = humanObj;
        }

}