package com.app;

import com.lobot.commands.controllers.Controller;
import com.lobot.commands.Validator;
import com.lobot.commands.domain.CmdEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.Collectors;

public class MainFrame extends JFrame implements KeyListener {

    private Controller controller;

    private JButton startProgramm;

    public MainFrame(Controller controller) {
        super();
        this.setBounds(0,0,500,500);
        this.controller = controller;
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int val;
        switch (e.getKeyChar()) {
            case 'q':
                val = Validator.validateFor(CmdEnum.HAND_CTRL, controller.status.get(CmdEnum.HAND_CTRL) + 5);
                controller.add(CmdEnum.HAND_CTRL, val).execute();
                break;
            case 'a':
                val = Validator.validateFor(CmdEnum.HAND_CTRL, controller.status.get(CmdEnum.HAND_CTRL) - 5);
                controller.add(CmdEnum.HAND_CTRL, val).execute();
                break;
            case 'w':
                val = Validator.validateFor(CmdEnum.ELBOW_CTRL, controller.status.get(CmdEnum.ELBOW_CTRL) + 5);
                controller.add(CmdEnum.ELBOW_CTRL, val).execute();
                break;
            case 's':
                val = Validator.validateFor(CmdEnum.ELBOW_CTRL, controller.status.get(CmdEnum.ELBOW_CTRL) - 5);
                controller.add(CmdEnum.ELBOW_CTRL, val).execute();
                break;
            case 'e':
                val = Validator.validateFor(CmdEnum.ARM_CTRL, controller.status.get(CmdEnum.ARM_CTRL) + 5);
                controller.add(CmdEnum.ARM_CTRL, val).execute();
                break;
            case 'd':
                val = Validator.validateFor(CmdEnum.ARM_CTRL, controller.status.get(CmdEnum.ARM_CTRL) - 5);
                controller.add(CmdEnum.ARM_CTRL, val).execute();
                break;

            default:
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String[] infos = this.controller.status.entrySet().stream().map(x-> x.getKey().name() + " : " + x.getValue()).collect(Collectors.toList()).toArray(new String[]{});
        for (int i = 0; i < infos.length; i++) {
            g.drawString(infos[i], 10, 20*i + 20);
        }
        draw(g, 200,350);
    }

    private void draw(Graphics g, int x0, int y0) {

        double handAngle = (controller.status.get(CmdEnum.HAND_CTRL) - 90) * Math.PI / 180;
        double elbowAngle = (controller.status.get(CmdEnum.ELBOW_CTRL) + controller.status.get(CmdEnum.HAND_CTRL) - 90) * Math.PI / 180;
        double armAngle = (controller.status.get(CmdEnum.ELBOW_CTRL) + controller.status.get(CmdEnum.HAND_CTRL) + controller.status.get(CmdEnum.ARM_CTRL)) * Math.PI / 180;

        int handLen = 100;
        int elbowLen = 100;
        int armLen = 50;

        int px = 0;
        int py = 0;
        int tx1;
        int ty1;
        int tx2;
        int ty2;

        g.drawOval(x0-3,y0-3, 6, 6);

        px = x0 + (int)Math.round(handLen * Math.cos(handAngle));
        py = y0 + (int)Math.round(handLen * Math.sin(handAngle));

        tx1 = x0 + (int)Math.round(handLen / 2 * Math.cos(handAngle - 0.8));
        ty1 = y0 + (int)Math.round(handLen / 2 * Math.sin(handAngle - 0.8));
        tx2 = x0 + (int)Math.round(handLen / 2 * Math.cos(handAngle + 0.5));
        ty2 = y0 + (int)Math.round(handLen / 2 * Math.sin(handAngle + 0.5));
        g.drawString("A", tx1, ty1);
        g.drawString("Q", tx2, ty2);

        if(controller.status.get(CmdEnum.HAND_CTRL) == Validator.HAND_ANGLE_MIN || controller.status.get(CmdEnum.HAND_CTRL) == Validator.HAND_ANGLE_MAX) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawLine(x0,y0, px, py);

        g.drawOval(px-3,py-3, 6, 6);
        g.setColor(Color.BLACK);

        x0 = px;
        y0 = py;

        tx1 = px + (int)Math.round(elbowLen / 2 * Math.cos(elbowAngle - 0.8));
        ty1 = py + (int)Math.round(elbowLen / 2 * Math.sin(elbowAngle - 0.8));
        tx2 = px + (int)Math.round(elbowLen / 2 * Math.cos(elbowAngle + 0.5));
        ty2 = py + (int)Math.round(elbowLen / 2 * Math.sin(elbowAngle + 0.5));
        g.drawString("S", tx1, ty1);
        g.drawString("W", tx2, ty2);

        px = px + (int)Math.round(elbowLen * Math.cos(elbowAngle));
        py = py + (int)Math.round(elbowLen * Math.sin(elbowAngle));

        if(controller.status.get(CmdEnum.ELBOW_CTRL) == Validator.ELBOW_ANGLE_MIN || controller.status.get(CmdEnum.ELBOW_CTRL) == Validator.ELBOW_ANGLE_MAX) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawLine(x0,y0, px, py);
        g.drawOval(px-3,py-3, 6, 6);
        g.setColor(Color.BLACK);

        x0 = px;
        y0 = py;

        tx1 = px + (int)Math.round(armLen / 2 * Math.cos(armAngle - 0.8));
        ty1 = py + (int)Math.round(armLen / 2 * Math.sin(armAngle - 0.8));
        tx2 = px + (int)Math.round(armLen / 2 * Math.cos(armAngle + 0.5));
        ty2 = py + (int)Math.round(armLen / 2 * Math.sin(armAngle + 0.5));
        g.drawString("D", tx1, ty1);
        g.drawString("E", tx2, ty2);

        px = px + (int)Math.round(armLen * Math.cos(armAngle));
        py = py + (int)Math.round(armLen * Math.sin(armAngle));

        if(controller.status.get(CmdEnum.ARM_CTRL) == Validator.ARM_ANGLE_MIN || controller.status.get(CmdEnum.ARM_CTRL) == Validator.ARM_ANGLE_MAX) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawLine(x0,y0, px, py);
        g.drawOval(px-3,py-3, 6, 6);
        g.setColor(Color.BLACK);

    }
}
