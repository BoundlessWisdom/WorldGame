package com.archonica.archons;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.networking.Communications;

import com.archonica.Archon;
import com.engine.core.CoreEngine;
import com.engine.core.Vector3f;
import com.engine.rendering.RenderingEngine;
import com.engine.core.*;

import static com.engine.core.Input.*;

public class User extends GameObject {
	GameInstance context = null;
	
	public String name = "";
	public boolean invisible = false;
	public boolean server = false;
	
	private final ArrayList<Archon> archons = new ArrayList<Archon>();
	Archon CurrentArchon = null;
	
	UserComs com;

	public class UserInput {
		boolean w;
		boolean a;
		boolean s;
		boolean d;

		boolean space;
		boolean shift;

		boolean rarrow;
		boolean larrow;
		
		public void UpdateInput()
		{
			if (Input.GetKey(KEY_A))
				a = true;
			else
				a = false;
			if (Input.GetKey(KEY_W))
				w = true;
			else
				w = false;
			if (Input.GetKey(KEY_S))
				s = true;
			else
				s = false;
			if (Input.GetKey(KEY_D))
				d = true;
			else
				d = false;
			if (Input.GetKey(KEY_SPACE))
				space = true;
			else
				space = false;
			if (Input.GetKey(KEY_LSHIFT) || Input.GetKey(KEY_RSHIFT))
				shift = true;
			else
				shift = false;
			if (Input.GetKey(KEY_RIGHT))
				rarrow = true;
			else
				rarrow = false;
			if (Input.GetKey(KEY_LEFT))
				larrow = true;
			else
				larrow = false;
		}
	}
	
	public class UserComs
	{
		User main;
		ArrayList<User> others;
		Communications com;
		boolean IsServer;
		
		public UserComs(User main, boolean IsServer) 
		{
			this.IsServer = IsServer;
			this.main = main;
			//com = CreateServer();
			others = new ArrayList<User>();
		}
		
		public UserComs(User main) 
		{
			this(main, false);
		}
		
		public void UpdateComs()
		{
			
		}
		
		private Communications CreateServer()
		{
			Communications c = null;
			
			try 
			{
				if (IsServer)
					c = Communications.Server();
				else
					c = Communications.Client();
			} catch (UnknownHostException | SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return c;
		}
	}
	
	final UserInput input = new UserInput();
	
	public User(String name, GameInstance context) 
	{
		this.name = name;
		this.context = context;
		com = new UserComs(this);
	}
	
	public User(String name, GameInstance context, Archon InitArchon) 
	{
		this(name, context);
		CurrentArchon = InitArchon;
		archons.add(CurrentArchon);
	}

	@Override
	public void Update(float delta) {
		super.Update(delta);
		input.UpdateInput();		
		UserUpdate(delta);
	}
	
	private void UserUpdate(float delta)
	{
		Vector3f v = new Vector3f(0,0,0);
		if(input.w)
		{
			v.Added(new Vector3f(0, 0, CurrentArchon.speed()));
		}
		
		if(input.s)
		{
			v.Added(new Vector3f(0, 0, -CurrentArchon.speed()));
		}
		
		if(input.a)
		{
			v.Added(new Vector3f(CurrentArchon.speed(), 0, 0));
		}
		
		if(input.d)
		{
			v.Added(new Vector3f(CurrentArchon.speed(), 0, 0));
		}
		
		if(input.space)
		{
			v.Added(new Vector3f(0, CurrentArchon.speed(), 0));
		}
		
		if(input.shift)
		{
			v.Added(new Vector3f(0, -CurrentArchon.speed(), 0));
		}
	}
	
	public RenderingEngine GetRenderingEngine() {
		return CoreEngine.GetRenderingEngine();
	}

}
