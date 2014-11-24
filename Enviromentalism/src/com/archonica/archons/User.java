package com.archonica.archons;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.networking.Communications;

import com.archonica.Archon;
import com.archonica.EntClass;
import com.engine.core.CoreEngine;
import com.engine.rendering.RenderingEngine;
import com.engine.core.*;

import static com.engine.core.Input.*;

public class User extends GameObject {
	boolean server = false;
	Communications com;
	
	private final ArrayList<Archon> archons = new ArrayList<Archon>();
	Archon CurrentArchon = null;

	public class UserInput {
		boolean a;
		boolean w;
		boolean s;
		boolean d;

		boolean space;

		boolean rarrow;
		boolean larrow;
	}

	final UserInput input = new UserInput();
	
	public User() 
	{
		com = CreateServer();
	}
	
	public User(Archon InitArchon) 
	{
		this();
		CurrentArchon = InitArchon;
		archons.add(CurrentArchon);
	}
	
	private Communications CreateServer()
	{
		Communications c = null;
		
		try 
		{
			if (server)
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

	@Override
	public void Update(float delta) {
		super.Update(delta);

		if (Input.GetKey(KEY_A))
			input.a = true;
		if (Input.GetKey(KEY_W))
			input.w = true;
		if (Input.GetKey(KEY_S))
			input.s = true;
		if (Input.GetKey(KEY_D))
			input.d = true;
		if (Input.GetKey(KEY_SPACE))
			input.space = true;
		if (Input.GetKey(KEY_RIGHT))
			input.rarrow = true;
		if (Input.GetKey(KEY_LEFT))
			input.larrow = true;

		UserUpdate(delta);
	}

	public void UserUpdate(float delta) {

	}

	public RenderingEngine GetRenderingEngine() {
		return CoreEngine.GetRenderingEngine();
	}

}
