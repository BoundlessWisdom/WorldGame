These are the steps to reading through this game engine. 

In the next two steps, do NOT look up the functions used. 
I know you, the reader, will probably not understand anything at first! Just take it all in and note what the classes you are reading extends. 

You must first start at the actual game (Archonica project). The main function is in Archonica.java.
Read through ArchonicaApp.java. This does all the game initializations(meshes, lighting, etc) and tells the game how to update. Note that it extends GameInstance. THIS IS IMPORTANT

Are you ready? Good! It's time to have some fun (or boredom if this type of stuff isn't enjoyable) and possible learn new things!

1. You must start with GameInstance (com.engine.core). This is what ArchonicaApp extended. It is where the camera is created, and where other key functions and lists are.
2. You see that GameInstance extends Game. Now go to Game in the same folder. 
