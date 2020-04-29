package opponent;

import java.awt.Graphics;
import java.io.IOException;

import automaton.Automaton;
import automaton.Category;
import automaton.Direction;
import game.Coord;
import game.Model;
import projectile.MagicProjectile;
import projectile.Projectile;

public class FlyingOpponent extends Opponent {
	
	protected boolean shooting, moving;
	
	long m_shot_time;

	public FlyingOpponent(Automaton automaton, int x, int y, Direction dir, Model model, int maxLife, int life,
			int attackSpeed, int resistance, int strength) throws IOException {
		
		super(automaton, x, y, dir, model, maxLife, life, attackSpeed, resistance, strength);
		
		m_shot_time = System.currentTimeMillis();
		
		shooting = false;
		moving = false;

	}
	
	@Override
	public boolean egg(Direction dir){
		
		long now = System.currentTimeMillis();

		if (now - m_shot_time > m_attackSpeed && !shooting) {

			shooting = true;
			
			shoot();

			m_shot_time = now;

			return true;
		}

		return false;
	}

	@Override
	public void paint(Graphics gp) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean cell(Direction dir, Category cat){
		
		if(cat.toString().equals("A")) {
			Coord playerCoord = m_model.getPlayer().getCoord();
			int player_x = playerCoord.X();
			int player_y = playerCoord.Y();
			int x = player_x - m_coord.X();
			int y = m_coord.Y() - m_height - player_y;
			
			int distance = (int) Math.sqrt(Math.pow(x,2) + Math.pow(y, 2));
			
			if(distance <= 400) {
				
				double r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
				double angle = (float) Math.asin(Math.abs(y) / r);
				int i = 0;
				
				while(i < distance) {
					int checkX;
					if (player_x > m_coord.X()) {
						checkX = (int) (m_coord.X() + i * Math.cos(angle));
					} else {
						checkX = (int) (m_coord.X() - i * Math.cos(angle));
					}
					if(m_model.m_room.isBlocked(checkX, (int)(m_coord.Y() - m_height - i * Math.sin(angle)))) {
						return false;
					}
					i += 40;
				}
				return true;
				
			}
			return false;
		}	
		return false;
	}
	
	public void shoot() {
		if (shooting) {
			int m_x = m_coord.X();
			int m_y = m_coord.Y() - m_height / 2;

			Direction direc;
			float angle;
			double r;
			Coord playerCoord = m_model.getPlayer().getCoord();
			int player_x = playerCoord.X();
			int player_y = playerCoord.Y();

			int x = player_x - m_x;
			int y = m_y - player_y;

			if (player_x > m_x) {
				direc = new Direction("E");
			} else {
				direc = new Direction("W");
			}

			r = (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			angle = (float) Math.asin(Math.abs(y) / r);

			if (player_y > m_y) {
				angle = -angle;
			}

			shooting = false;

			try {
				addProjectile(m_x, m_y, angle, this, direc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addProjectile(int x, int y, double angle, FlyingOpponent opponent, Direction direction) throws Exception {
		m_projectiles.add(new MagicProjectile(m_model.arrowAutomaton, x, y, angle, opponent, direction));
	}

	public void removeProjectile(Projectile projectile) {
		m_projectiles.remove(projectile);
	}

}
