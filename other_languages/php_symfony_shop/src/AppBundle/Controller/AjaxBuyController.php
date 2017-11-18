<?php

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Psr\Log\LoggerInterface;

class AjaxBuyController extends Controller
{
    /**
     * @Route("/buy", name="buy")
     */
    public function indexAction(Request $request)
    {
        $logger = $this->get('logger');

    	$item = $request->request->get('item');
    	$quantity = $request->request->get('quantity');

        $logger->info("item:[".$item."], quantity:[".$quantity."] is added into shopping cart.");

        $total_quantity = 0;
       	if(empty ($_SESSION['shopping_cart'])) {
            $shopping_cart = array(
                $item => $quantity
                );
    		$_SESSION['shopping_cart'] = $shopping_cart;
            $total_quantity = $quantity;
    	} else {
            $shopping_cart = $_SESSION['shopping_cart'];
            if(empty($shopping_cart[$item])){
                $shopping_cart[$item] = $quantity; 
            }else {
                $shopping_cart[$item] += $quantity; 
            }
                      
            $_SESSION['shopping_cart'] = $shopping_cart;

            foreach ($shopping_cart as $key => $value) {
                $total_quantity += $value;
            }
        }

        $arrData = ['total_quantity' => $total_quantity];
        return new JsonResponse($arrData);
    }
}
