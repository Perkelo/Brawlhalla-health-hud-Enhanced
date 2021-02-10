package me.buffsee.bhh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Main {

	static boolean smashSelected = false;
	static boolean twosMode = false;
	static SmashWindowObject smashWindows[] = {null, null, null, null};

	static boolean shift = false;
	static boolean control = false;
	static boolean alt = false;
	static boolean one = false;
	static boolean two = false;
	static boolean three = false;
	static boolean four = false;

	enum EditingMode{
		TextSize,
		ImageSize,
		TextAndImageSize,
		TextAndImageTransparency,
		GlobalPosition,
		Nothing
	}

	static Settings settings[] = {
			new Settings(0, 0, 0, 0, 0),
			new Settings(0, 0, 0, 0, 0)
	};

	static EditingMode currentEditingMode = EditingMode.Nothing;
	
	public static void main(String[] args) {
		settings[0] = Settings.fromJSON(Main.readFile("ones.json"));
		settings[1] = Settings.fromJSON(Main.readFile("twos.json"));

		// might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails 
		GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // use false here to switch to hook instead of raw input

		System.out.println("Global keyboard hook successfully started, press [escape] key to shutdown. Connected keyboards:");
		for(Entry<Long,String> keyboard:GlobalKeyboardHook.listKeyboards().entrySet())
			System.out.format("%d: %s\n", keyboard.getKey(), keyboard.getValue());
		
		keyboardHook.addKeyListener(new GlobalKeyAdapter() {
			
			@Override public void keyPressed(GlobalKeyEvent event) {
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F9) {
					System.exit(0);
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_SHIFT) {
					shift = true;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) {
					control = true;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_MENU) {
					alt = true;
				}


				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F1) {
					currentEditingMode = EditingMode.TextAndImageSize;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F2) {
					currentEditingMode = EditingMode.ImageSize;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F3) {
					currentEditingMode = EditingMode.TextSize;
				}


				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F4) {
					currentEditingMode = EditingMode.TextAndImageTransparency;
				}

				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F5) {
					currentEditingMode = EditingMode.GlobalPosition;
				}


				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F8){
					Settings settings = smashWindows[0].getSettings();
					Main.writeFile(twosMode ? "twos.json" : "ones.json", settings.toJSON());
				}


				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT){
					try {
						switch (currentEditingMode) {
							case TextSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeText(-5);
								}
								break;
							case ImageSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeImage(-0.1f);
								}
								break;
							case TextAndImageSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeText(-5);
									window.resizeImage(-0.1f);
								}
								break;
							case TextAndImageTransparency:
								for (SmashWindowObject window : smashWindows) {
									window.changeTransparency(-0.1f);
								}
								break;
							case GlobalPosition:
								for (SmashWindowObject window : smashWindows) {
									window.moveX(-6);
								}
								break;
							case Nothing:
								break;
						}
					}catch(Exception e){
						System.err.println(e.getLocalizedMessage());
					}
				}


				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT){
					try {
						switch (currentEditingMode) {
							case TextSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeText(+5);
								}
								break;
							case ImageSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeImage(+0.1f);
								}
								break;
							case TextAndImageSize:
								for (SmashWindowObject window : smashWindows) {
									window.resizeText(+5);
									window.resizeImage(+0.1f);
								}
								break;
							case TextAndImageTransparency:
								for (SmashWindowObject window : smashWindows) {
									window.changeTransparency(+0.1f);
								}
								break;
							case GlobalPosition:
								for (SmashWindowObject window : smashWindows) {
									window.moveX(+6);
								}
								break;
							case Nothing:
								break;
						}
					}catch(Exception e){
						System.err.println(e.getLocalizedMessage());
					}
				}

				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP){
					try {
						if (currentEditingMode == EditingMode.GlobalPosition) {
							for (SmashWindowObject window : smashWindows) {
								window.moveY(-6);
							}
						}
					}catch(Exception e){}
				}

				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN){
					try {
						if (currentEditingMode == EditingMode.GlobalPosition) {
							for (SmashWindowObject window : smashWindows) {
								window.moveY(+6);
							}
						}
					}catch(Exception e){}
				}
			}

			@Override
			public void keyReleased(GlobalKeyEvent event) {
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_SHIFT) {
					shift = false;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_CONTROL) {
					control = false;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_MENU) {
					alt = false;
				}
				if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_F1 ||
					event.getVirtualKeyCode() == GlobalKeyEvent.VK_F2 ||
					event.getVirtualKeyCode() == GlobalKeyEvent.VK_F3 ||
					event.getVirtualKeyCode() == GlobalKeyEvent.VK_F4 ||
					event.getVirtualKeyCode() == GlobalKeyEvent.VK_F5) {
					currentEditingMode = EditingMode.Nothing;
				}
			}
		});
		
		
		JFrame asktwo = new JFrame("1v1 or 2v2?");
		asktwo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("1v1 or 2v2?");
		label.setLocation(60, 0);
		label.setSize(200, 20);
		JCheckBox smash = new JCheckBox();
		smash.setLocation(120, 53);
		smash.setSize(25, 25);
		JLabel smashlabel = new JLabel("Smash mode:");
		smashlabel.setLocation(30, 54);
		smashlabel.setSize(200, 20);
		JButton one = new JButton("1v1");
		one.setLocation(30, 30);
		one.setSize(54, 20);
		one.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(smash.isSelected()) {
					smashSelected = true;
					twosMode = false;
					Smash smash = new Smash();
					smash.setTwos(false);
					smash.run();
					asktwo.setVisible(false);
				} else {
					BHH bhhthread = new BHH();
					bhhthread.setTwos(false);
					bhhthread.start();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					bhhthread.showWindow();
					asktwo.setVisible(false);
				}
			}
			
		});
		JButton two = new JButton("2v2");
		two.setLocation(100, 30);
		two.setSize(54, 20);
		two.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(smash.isSelected()) {
					smashSelected = true;
					twosMode = true;
					Smash smash = new Smash();
					smash.setTwos(true);
					smash.start(); //TODO: Replace these with run();
					asktwo.setVisible(false);
				} else {
					BHH bhhthread = new BHH();
					bhhthread.setTwos(true);
					bhhthread.start();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					bhhthread.showWindow();
					asktwo.setVisible(false);
			}
			}
		});
		asktwo.setLayout(null);
		asktwo.add(smashlabel);
		asktwo.add(smash);
		asktwo.add(one);
		asktwo.add(two);
		asktwo.add(label);
		asktwo.setSize(200, 120);
		asktwo.setLocationRelativeTo(null);
		asktwo.setVisible(true);

		
		TaskbarHider taskbarhider = new TaskbarHider();
		taskbarhider.hideTaskbar();
	}

	public static void applySettings(SmashWindowObject window){
		Settings settings;
		if(twosMode) {
			settings = Main.settings[1];
		}else{
			settings = Main.settings[0];
		}

		try {
			window.resizeImage(settings.imageSize);
			window.resizeText(settings.textSize);
			window.changeTransparency(settings.transparency);
			window.moveX(settings.xPosition);
			window.moveY(settings.yPosition);
		}catch(Exception e){}

	}

	public static File createFile(String filename) throws IOException{
		File file = new File(filename);
		if(file.exists()){
			return file;
		}
		file.createNewFile();
		return file;
	}

	public static void writeFile(String filename, String contents){
		try {
			createFile(filename);
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.write(contents);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static String readFile(String filename){
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			StringBuilder strbldr = new StringBuilder();
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				strbldr.append(data).append("\n");
			}
			scanner.close();
			return strbldr.toString();
		} catch (FileNotFoundException e) {
			return "";
		}
	}
}
