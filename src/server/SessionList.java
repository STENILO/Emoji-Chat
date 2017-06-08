/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mikkel
 */
public class SessionList {

	private final Map<String, Integer> NameToId;
	private final Map<Integer, SessionInfo> IdToInfo;

	public SessionList() {
		this.IdToInfo = new HashMap<>();
		this.NameToId = new HashMap<>();
	}

	public void put(String name, int id, int lastMSG) {
		// Check if we already have the given session id
		// This shouldn't happen
		if (IdToInfo.containsKey(id)) {
			return;
		}

		// If this user already have a session
		// we remove the old one
		if (NameToId.containsKey(name)) {
			IdToInfo.remove(NameToId.get(name));
		}

		NameToId.put(name, id);
		SessionInfo newInfo = new SessionInfo();
		newInfo.id = id;
		newInfo.name = name;
		newInfo.lastMessage = lastMSG;
		IdToInfo.put(id, newInfo);
	}

	public boolean update(int id, SessionInfo info) {
		if (NameToId.containsKey(info.name) && IdToInfo.containsKey(id)) {
			IdToInfo.put(id, info);
			return true;
		}
		return false;
	}

	public SessionInfo getSession(int id) {
		return IdToInfo.get(id);
	}

	public Integer getId(String name) {
		return NameToId.get(name);
	}

	public Set<Map.Entry<Integer, SessionInfo>> entrySet() {
		return IdToInfo.entrySet();
	}

	public void remove(int id) {
		if (IdToInfo.containsKey(id)) {
			SessionInfo info = IdToInfo.get(id);
			if(info != null){
				NameToId.remove(info.name);
			}
			IdToInfo.remove(id);
		}
	}
}
